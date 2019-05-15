package br.org.otus.laboratory.participant;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.participant.validators.AliquotDeletionValidator;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidator;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryExtractionDao;
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
  private AliquotService aliquotService;
  @Inject
  private CollectGroupRaffle groupRaffle;
  @Inject
  private ExamLotDao examLotDao;
  @Inject
  private AliquotDao aliquotDao;
  @Inject
  private TransportationLotDao transportationLotDao;
  @Inject
  private ExamUploader examUploader;
  @Inject
  private ParticipantLaboratoryExtractionDao participantLaboratoryExtractionDao;

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
    ParticipantLaboratory participantLaboratory = participantLaboratoryDao.findByRecruitmentNumber(recruitmentNumber);
    List<Aliquot> aliquots = aliquotService.getAliquots(recruitmentNumber);
    participantLaboratory.setAliquots(aliquots);

    return participantLaboratory;
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
  public Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException {
    return participantLaboratoryDao.updateTubeCollectionData(rn, tube);
  }

  @Override
  public ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquotsDTO) throws DataNotFoundException, ValidationException {
    ParticipantLaboratory participantLaboratory = getLaboratory(updateAliquotsDTO.getRecruitmentNumber());
    ParticipantLaboratoryValidator aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, aliquotDao, participantLaboratory);

    aliquotUpdateValidator.validate();

    Participant participant = participantDao.findByRecruitmentNumber(updateAliquotsDTO.getRecruitmentNumber());

    updateAliquotsDTO.getUpdateTubeAliquots().forEach(updateTubeAliquotsDTO -> updateTubeAliquotsDTO.getAliquots().forEach(simpleAliquot -> {
      Aliquot aliquot = new Aliquot(simpleAliquot);
      aliquot.setTubeCode(updateTubeAliquotsDTO.getTubeCode());
      aliquot.setParticipatData(participant);
      aliquotDao.persist(aliquot);
    }));

    CompletableFuture.supplyAsync(() -> {
      try{
        aliquotDao.executeFunction("syncResults()");
      } catch (Exception e) {
        new Exception("Error while syncing results", e).printStackTrace();
      }
      return null;
    });


    return getLaboratory(updateAliquotsDTO.getRecruitmentNumber());
  }

  @Override
  public void deleteAliquot(String code) throws ValidationException, DataNotFoundException {
    AliquotDeletionValidator validator = new AliquotDeletionValidator(code, this.aliquotDao, this.examUploader, this.examLotDao, this.transportationLotDao);
    validator.validate();
    aliquotDao.delete(code);
  }

  @Override
  public LinkedList<LaboratoryRecordExtraction> getLaboratoryExtraction() throws DataNotFoundException {
    return participantLaboratoryExtractionDao.getLaboratoryExtraction();
  }

  @Override
  public String convertAliquotRole(Aliquot convertedAliquot) {
    return aliquotDao.convertAliquotRole(convertedAliquot);
  }
}
