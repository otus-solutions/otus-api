package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import service.ProjectConfigurationService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
public class ParticipantServiceBean implements ParticipantService {

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private ProjectConfigurationService projectConfigurationService;

  @Inject
  private RecruitmentNumberService recruitmentNumberService;

  @Override
  public void create(Set<Participant> participants) {
    ArrayList<Participant> insertedParticipants = new ArrayList<>();
    participants.forEach(participant -> {
      try {
        insertedParticipants.add(create(participant));
      } catch (ValidationException | DataNotFoundException e) {
        insertedParticipants.add(null);
      }

    });
  }

  @Override
  public ArrayList<Long> listCenterRecruitmentNumbers(String center) throws DataNotFoundException {
    return participantDao.getRecruitmentNumbersByFieldCenter(center);
  }

  @Override
  public void registerPassword(String email, String password) throws DataNotFoundException {
    participantDao.registerPassword(email, password);
  }

  @Override
  public Participant create(Participant participant) throws ValidationException, DataNotFoundException {
    if (projectConfigurationService.isRnProvided()) {
      Long rn = recruitmentNumberService.get(participant.getFieldCenter().getAcronym());

      participant.setRecruitmentNumber(rn);
      participantDao.persist(participant);

      return participant;

    } else {
      recruitmentNumberService.validate(participant.getFieldCenter(), participant.getRecruitmentNumber());

      if (participantDao.exists(participant.getRecruitmentNumber())) {
        String error = "RecruimentNumber {" + participant.getRecruitmentNumber().toString() + "} already exists.";
        throw new ValidationException(new Throwable(error));
      }
      participantDao.persist(participant);
      return participant;
    }
  }

  @Override
  public Participant update(Participant participant) throws ValidationException, DataNotFoundException {
    if (participantDao.exists(participant.getRecruitmentNumber())) {
      participantDao.update(participant);
    } else {
      String error = "RecruimentNumber {" + participant.getRecruitmentNumber().toString() + "} not exists.";
      throw new ValidationException(new Throwable(error));
    }
    return participant;
  }

  @Override
  public List<Participant> list(FieldCenter fieldCenter) {
    if (fieldCenter == null) {
      return participantDao.find();
    } else {
      return participantDao.findByFieldCenter(fieldCenter);
    }
  }

  @Override
  public Participant getByRecruitmentNumber(Long rn) throws DataNotFoundException {
    return participantDao.findByRecruitmentNumber(rn);
  }

  @Override
  public Participant getByEmail(String email) throws DataNotFoundException {
    return participantDao.fetchByEmail(email);
  }

  @Override
  public ObjectId findIdByRecruitmentNumber(Long rn) throws DataNotFoundException {
    return participantDao.findIdByRecruitmentNumber(rn);
  }

  @Override
  public Long getPartipantsActives(String acronymCenter) throws DataNotFoundException {
    return participantDao.countParticipantActivities(acronymCenter);
  }

  @Override
  public String getParticipantFieldCenterByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    return participantDao.getParticipantFieldCenterByRecruitmentNumber(recruitmentNumber);
  }

  @Override
  public Participant getParticipant(ObjectId id) throws DataNotFoundException {
    return  participantDao.getParticpant(id);
  }

  @Override
  public Boolean updateEmail(ObjectId participantId, String email) throws DataNotFoundException, AlreadyExistException {
    return participantDao.updateEmail(participantId, email);
  }

  @Override
  public String getEmail(String participantId) throws ValidationException, DataNotFoundException {
    if(!ObjectId.isValid(participantId)) {
      throw new ValidationException(new Throwable("ObjectId is not valid"));
    }
    ObjectId id = new ObjectId(participantId);
    return participantDao.getEmail(id);
  }

  @Override
  public Boolean deleteEmail(ObjectId participantId) throws DataNotFoundException {
    return participantDao.deleteEmail(participantId);
  }

  @Override
  public List<Participant> getByFieldCenter(String centerAcronym) throws MemoryExcededException {
    return participantDao.getByFieldCenter(centerAcronym);
  }
}
