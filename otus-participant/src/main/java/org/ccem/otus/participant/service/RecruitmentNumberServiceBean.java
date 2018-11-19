package org.ccem.otus.participant.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RecruitmentNumberServiceBean implements RecruitmentNumberService{
  public static final Integer RN_MIN_SIZE = 7;
  public static final Integer INITIAL_VALUE = 1;


  @Inject
  private ParticipantDao participantDao;

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Override
  public Long get(String centerAcronym) throws DataNotFoundException, ValidationException {

    FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(centerAcronym);

    if(fieldCenter == null) {
      throw new DataNotFoundException("Field center not found: " + centerAcronym);
    }

    try {
      Participant lastInsertion = participantDao.getLastInsertion(fieldCenter);
      return getNextRecruitmentNumber(lastInsertion.getRecruitmentNumber(), fieldCenter);

    } catch (DataNotFoundException e) {

      return createFirst(fieldCenter.getCode());
    }
  }

  @Override
  public void validate(FieldCenter fieldCenter, Long recruitmentNumber) throws ValidationException {
    if (recruitmentNumber == null) {
      throw new ValidationException(new Throwable("RecruimentNumber not provided"));
    }

    String rnString = recruitmentNumber.toString();
    String codeString = fieldCenter.getCode().toString();

    String prefix = rnString.substring(0, codeString.length());

    if(!prefix.equals(codeString)) {
      throw new ValidationException(new Throwable("RN inconsistency. RN " + rnString + " does not match with given center: " + fieldCenter.getAcronym()));
    }
  }

  private Long getNextRecruitmentNumber(Long rn, FieldCenter fieldCenter) throws ValidationException {
    this.validate(fieldCenter,rn);

    String rnString = rn.toString();
    String codeString = fieldCenter.getCode().toString();

    String substring = rnString.substring(codeString.length());
    Long aLong = new Long(substring) + 1 ;

    String newRnString =  padZeroes(aLong.toString(), RN_MIN_SIZE -1);
    return new Long(codeString + newRnString);
  }

  private Long createFirst (Integer centerCode) {
    String radical = padZeroes(INITIAL_VALUE.toString(), RN_MIN_SIZE - 1);
    return new Long (centerCode + radical);
  }

  private String padZeroes (String s, Integer maximumSize) {
    StringBuilder prefix = new StringBuilder();

    for (int i = 0; i < maximumSize - s.length(); i ++) {
      prefix.append("0");
    }

    return prefix.toString() + s;

  }

}
