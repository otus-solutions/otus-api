package br.org.otus.laboratory.participant;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.DeletionException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.participant.validators.AliquotDeletionValidator;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidator;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryValidator;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@Stateless
public class ParticipantLaboratoryServiceBean implements ParticipantLaboratoryService {

  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Inject
  private ParticipantDao participantDao;
  @Inject
  private TubeService tubeService;
  @Inject
  private CollectGroupRaffle groupRaffle;
  @Inject
  private ExamLotDao examLotDao;
  @Inject
  private TransportationLotDao transportationLotDao;
  @Inject
  private ExamUploader examUploader;

  @Override
  public boolean hasLaboratory(Long recruitmentNumber) {
    try {
      participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
      return true;
    } catch (DataNotFoundException e) {
      return false;
    }
  }

  @Override
  public ParticipantLaboratory getLaboratory(Long recruitmentNumber) throws DataNotFoundException {
    return participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
  }

  @Override
  public ParticipantLaboratory create(Long recruitmentNumber) throws DataNotFoundException {
    Participant participant = participantDao.findByRecruitmentNumber(recruitmentNumber);
    CollectGroupDescriptor collectGroup = groupRaffle.perform(participant);
    List<Tube> tubes = tubeService.generateTubes(TubeSeed.generate(participant, collectGroup));
    ParticipantLaboratory laboratory = new ParticipantLaboratory(recruitmentNumber, collectGroup.getName(), tubes);
    participantLaboratoryDao.persist(laboratory);
    return laboratory;
  }

  @Override
  public ParticipantLaboratory update(ParticipantLaboratory partipantLaboratory) throws DataNotFoundException {
    return participantLaboratoryDao.updateLaboratoryData(partipantLaboratory);
  }

  @Override
  public Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException {
    return participantLaboratoryDao.updateTubeCollectionData(rn, tube);
  }

  @Override
  public ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquotsDTO) throws DataNotFoundException, ValidationException {
    ParticipantLaboratory participantLaboratory = getLaboratory(updateAliquotsDTO.getRecruitmentNumber());
    ParticipantLaboratoryValidator aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, participantLaboratoryDao, participantLaboratory);

    try {
      aliquotUpdateValidator.validate();
    } catch (Exception e) {
      throw e;
    }
    syncronizedParticipantLaboratory(updateAliquotsDTO, participantLaboratory);
    return update(participantLaboratory);
  }

  @Override
  public ArrayList<Aliquot> getAllAliquots() {
    return participantLaboratoryDao.getFullAliquotsList();
  }

  @Override
  public ArrayList<Aliquot> getAllAliquots(String fieldCenter) {
    return null;
  }

  @Override
  public void deleteAliquot(String code) throws DeletionException, DataNotFoundException {
    AliquotDeletionValidator validator = new AliquotDeletionValidator(code, this.examLotDao, this.transportationLotDao, this.examUploader);
    validator.validate();
    this.participantLaboratoryDao.deleteAliquot(code);
  }

  private void syncronizedParticipantLaboratory(UpdateAliquotsDTO updateAliquotsDTO, ParticipantLaboratory participantLaboratory) {
    for (UpdateTubeAliquotsDTO aliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
      for (Tube tube : participantLaboratory.getTubes()) {
        if (tube.getCode().equals(aliquotDTO.getTubeCode())) {
          tube.addAllAliquotsThatNotContainsInList(aliquotDTO.getAliquots());
        }
      }
    }
  }
}
