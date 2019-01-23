package br.org.otus.persistence;

import br.org.otus.attachments.AttachmentsReport;

public interface AttachmentsExtractionDao {

    AttachmentsReport fetchAttachmentsReport(String acronym, Integer version);
}
