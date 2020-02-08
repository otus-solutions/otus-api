package org.ccem.otus.participant.importation.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.importation.model.ParticipantImport;

import java.util.Set;

public interface ParticipantImportService {

  void importation(Set<ParticipantImport> participantImports) throws ValidationException, DataNotFoundException;
}
