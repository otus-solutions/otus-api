package org.ccem.otus.participant.importation.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.builder.ParticipantBuilder;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;

@Stateless
public class ParticipantImportServiceBean implements ParticipantImportService {

  @Inject
  private ParticipantService participantService;

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Inject
  private ParticipantImportValidatorService participantImportValidatorService;

  @Override
  public void importation(Set<ParticipantImport> participantImports) throws ValidationException, DataNotFoundException {
    performImportation(participantImports);
  }

  private void performImportation(Set<ParticipantImport> participantImports) throws ValidationException, DataNotFoundException {
    participantImportValidatorService.isImportable(participantImports);
    ParticipantBuilder participantBuilder = new ParticipantBuilder(fieldCenterDao.find());

    for (ParticipantImport participantImport : participantImports) {
      Participant participant = participantBuilder.buildFromPartipantToImport(participantImport);
      participantService.create(participant);
    }
  }

}
