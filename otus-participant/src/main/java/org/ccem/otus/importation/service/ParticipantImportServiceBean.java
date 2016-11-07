package org.ccem.otus.importation.service;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.service.ParticipantService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;

@Stateless
public class ParticipantImportServiceBean implements ParticipantImportService {
    @Inject
    private ParticipantService participantService;

    @Override
    public void importation(Set<ParticipantImport> participantImports) {
        // TODO realizar a construção dos participantes para importação.
    }
}
