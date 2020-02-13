package br.org.otus.laboratory.unattached.service;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.DTOs.ValidationErrorResponseDTO;
import br.org.otus.laboratory.unattached.UnattachedLaboratoryDao;
import br.org.otus.laboratory.unattached.enums.UnattachedLaboratoryActions;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UnattachedLaboratoryServiceBean implements UnattachedLaboratoryService {

  @Inject
  private TubeService tubeService;
  @Inject
  private UnattachedLaboratoryDao unattachedLaboratoryDao;
  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;

  @Override
  public void create(String userEmail, Integer unattachedLaboratoryLastInsertion, CollectGroupDescriptor collectGroupDescriptor, FieldCenter fieldCenter) {
    List<Tube> tubes = tubeService.generateTubes(TubeSeed.generate(fieldCenter, collectGroupDescriptor));
    UnattachedLaboratory laboratory = new UnattachedLaboratory(unattachedLaboratoryLastInsertion, fieldCenter.getAcronym(), collectGroupDescriptor.getName(), tubes);
    laboratory.addUserHistory(userEmail, UnattachedLaboratoryActions.CREATED);
    unattachedLaboratoryDao.persist(laboratory);
  }

  @Override
  public ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) throws DataNotFoundException {
    return unattachedLaboratoryDao.find(fieldCenterAcronym, collectGroupDescriptorName, page, quantityByPage);
  }

  @Override
  public void attache(Long recruitmentNumber, String userEmail, int laboratoryIdentification, String participantCollectGroupName, String participantFieldCenterAcronym) throws DataNotFoundException, ValidationException {
    try {
      participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
      throw new ValidationException(new Throwable("Participant already have a laboratory"));
    } catch (DataNotFoundException e) {
      UnattachedLaboratory unattachedLaboratory = unattachedLaboratoryDao.find(laboratoryIdentification);
      if (unattachedLaboratory.getAvailableToAttache()) {
        if (!unattachedLaboratory.getFieldCenterAcronym().equals(participantFieldCenterAcronym) || !unattachedLaboratory.getCollectGroupName().equals(participantCollectGroupName)) {
          throw new ValidationException(new Throwable("Invalid configuration"), new ValidationErrorResponseDTO(participantFieldCenterAcronym, unattachedLaboratory.getFieldCenterAcronym(), participantCollectGroupName, unattachedLaboratory.getCollectGroupName()));
        }
        ParticipantLaboratory participantLaboratory = new ParticipantLaboratory(recruitmentNumber, participantCollectGroupName, unattachedLaboratory.getTubes());
        participantLaboratoryDao.persist(participantLaboratory);

        unattachedLaboratory.disable();
        unattachedLaboratory.addUserHistory(userEmail, UnattachedLaboratoryActions.ATTACHED);
        unattachedLaboratoryDao.update(unattachedLaboratory.getIdentification(), unattachedLaboratory);
      } else {
        if (unattachedLaboratory.getLastHistory().getAction().equals(UnattachedLaboratoryActions.ATTACHED)) {
          throw new ValidationException(new Throwable("Laboratory is already attached"));
        } else {
          throw new ValidationException(new Throwable("Laboratory is removed"));
        }
      }
    }
  }
}
