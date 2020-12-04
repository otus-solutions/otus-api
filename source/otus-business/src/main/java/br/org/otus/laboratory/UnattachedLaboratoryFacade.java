package br.org.otus.laboratory;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import br.org.otus.laboratory.unattached.service.UnattachedLaboratoryService;

import javax.inject.Inject;

public class UnattachedLaboratoryFacade {

  @Inject
  private UnattachedLaboratoryService unattachedLaboratoryService;
  @Inject
  private FieldCenterDao fieldCenterDao;
  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;
  @Inject
  private ParticipantDao participantDao;
  @Inject
  private CollectGroupRaffle groupRaffle;

  public void create(String userEmail, String fieldCenterAcronym, String collectGroupDescriptorName) {
    LaboratoryConfiguration laboratoryConfiguration = null;
    try{
      laboratoryConfiguration = laboratoryConfigurationService.getLaboratoryConfiguration();
    }
    catch (DataNotFoundException e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }

    CollectGroupDescriptor collectGroupDescriptor;
    try {
      collectGroupDescriptor = laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(collectGroupDescriptorName);
    } catch (Exception e) {
      throw new HttpResponseException(Validation.build("Collect group not found"));
    }

    FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(fieldCenterAcronym);
    Integer lastInsertion = laboratoryConfigurationService.updateUnattachedLaboratoryLastInsertion();
    unattachedLaboratoryService.create(userEmail, lastInsertion, collectGroupDescriptor, fieldCenter);
  }

  public ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) {
    try {
      return unattachedLaboratoryService.find(fieldCenterAcronym, collectGroupDescriptorName, page, quantityByPage);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void attache(String email, int laboratoryIdentification, Long recruitmentNumber) {
    try {
      Participant participant = participantDao.findByRecruitmentNumber(recruitmentNumber);
      if (!participant.isIdentified()){
        throw new HttpResponseException(Validation.build("Participant not identified"));
      }

      CollectGroupDescriptor collectGroup = groupRaffle.perform(participant);
      unattachedLaboratoryService.attache(recruitmentNumber, email, laboratoryIdentification, collectGroup.getName(), participant.getFieldCenter().getAcronym());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage(), e.getData()));
    }
  }

  public void discard(String userEmail, String laboratoryOid) {
    try {
      unattachedLaboratoryService.discard(userEmail, laboratoryOid);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public UnattachedLaboratory findById(String laboratoryOid) {
    try {
      return unattachedLaboratoryService.findById(laboratoryOid);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public UnattachedLaboratory findByIdentification(int laboratoryIdentification) {
    try {
      return unattachedLaboratoryService.findByIdentification(laboratoryIdentification);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
}
