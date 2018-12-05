package org.ccem.otus.participant.importation.service;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

@Stateless
public class ParticipantImportValidatorServiceBean implements ParticipantImportValidatorService {

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Inject
  private ParticipantDao participantDao;

  private ArrayList<FieldCenter> fieldCenters;
  private ArrayList<String> availableFieldCenterAcronyms;
  private ArrayList<Participant> registeredParticipants;
  private ArrayList<Long> recruitmentNumberList;

  private void setUp() {
    getAvailableFieldCenterAcronyms();
    getRecruitmentNumberList();
  }

  @Override
  public boolean isImportable(Set<ParticipantImport> participantToImports) throws ValidationException {
    setUp();

    if (availableFieldCenterAcronyms.isEmpty()) {
      throw new ValidationException(new Throwable("There are no registered FieldCenters."));
    }

    for (ParticipantImport participantImport : participantToImports) {
      hasValidAttribute(participantImport);
      hasValidFieldCenter(participantImport);
    }

    if (!recruitmentNumberList.isEmpty()) {
      for (ParticipantImport participantImport : participantToImports) {
        isAvailableRecruitmentNumber(participantImport);
      }
    }

    return true;
  }

  private void hasValidAttribute(ParticipantImport participantToImport) throws ValidationException {
    if (participantToImport.containsNullAttribute())
      throw new ValidationException(new Throwable("Invalid attribute. " + participantToImport.toString()));
  }

  private void hasValidFieldCenter(ParticipantImport participantToImport) throws ValidationException {
    if (!availableFieldCenterAcronyms.contains(participantToImport.getCenter())) {
      throw new ValidationException(new Throwable("Invalid field center. " + participantToImport.toString()));
    }
  }

  private void isAvailableRecruitmentNumber(ParticipantImport participantToImport) throws ValidationException {
    if (recruitmentNumberList.contains(participantToImport.getRn())) {
      throw new ValidationException(new Throwable("Recruitment number already exists. " + participantToImport.toString()));
    }
  }

  private ArrayList<Long> getRecruitmentNumberList() {
    recruitmentNumberList = new ArrayList<Long>();
    getRegisteredParticipants().forEach(registeredParticipant -> recruitmentNumberList.add(registeredParticipant.getRecruitmentNumber()));
    return recruitmentNumberList;
  }

  private ArrayList<Participant> getRegisteredParticipants() {
    registeredParticipants = new ArrayList<Participant>();
    registeredParticipants = participantDao.find();
    return registeredParticipants;
  }

  private ArrayList<String> getAvailableFieldCenterAcronyms() {
    availableFieldCenterAcronyms = new ArrayList<String>();
    getAllFieldCenters().forEach(fieldCenter -> availableFieldCenterAcronyms.add(fieldCenter.getAcronym()));
    return availableFieldCenterAcronyms;
  }

  private ArrayList<FieldCenter> getAllFieldCenters() {
    fieldCenters = new ArrayList<FieldCenter>();
    fieldCenters = fieldCenterDao.find();
    return fieldCenters;
  }

}
