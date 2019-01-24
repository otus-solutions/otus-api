package br.org.otus.persistence;

import br.org.otus.attachments.AttachmentsReport;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface AttachmentsExtractionDao {

    AttachmentsReport fetchAttachmentsReport(String acronym, Integer version) throws DataNotFoundException;
}
