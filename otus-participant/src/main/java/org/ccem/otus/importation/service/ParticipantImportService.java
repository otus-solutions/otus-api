package org.ccem.otus.importation.service;

import org.ccem.otus.importation.model.ParticipantImport;

import java.util.Set;

public interface ParticipantImportService {

    void importation(Set<ParticipantImport> participantImports);
}
