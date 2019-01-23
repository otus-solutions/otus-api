package br.org.otus.api;

import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.persistence.AttachmentsExtractionDao;
import br.org.otus.persistence.builder.AttachmentsExtractionQueryBuilder;
import br.org.otus.service.CsvWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExtractionServiceBean implements ExtractionService{
	private static final char DELIMITER = ';';
	private static final String RECORD_SEPARATOR = "\n";

	private CSVFormat csvFileFormat;
	private CSVPrinter csvFilePrinter;
	private ByteArrayOutputStream out;

	@Inject
	private AttachmentsExtractionDao attachmentsExtractionDao;

	@Override
	public byte[] createExtraction(Extractable extractionInterface) throws DataNotFoundException {
    CsvWriter csvWriter = new CsvWriter();
    csvWriter.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriter.getResult();
	}

	@Override
	public byte[] getAttachmentsReport(String acronym,Integer version) {

		AttachmentsReport attachmentsReport = attachmentsExtractionDao.fetchAttachmentsReport(acronym, version);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out = new ByteArrayOutputStream();
			csvFileFormat = CSVFormat.newFormat(DELIMITER).withRecordSeparator(RECORD_SEPARATOR).withQuote('\"').withQuoteMode(QuoteMode.MINIMAL);
			csvFilePrinter = new CSVPrinter(new PrintWriter(out), csvFileFormat);

			csvFilePrinter.printRecord(
					"recruitmentNumber",
					"questionId",
					"archiveId",
					"status",
					"status"
			);

			for(AttachmentsReport.Attachment attachment : attachmentsReport.getAttachmentsList()){
				csvFilePrinter.printRecord(
						attachment.getRecruitmentNumber(),
						attachment.getQuestionId(),
						attachment.getArchiveId(),
						attachment.getStatus(),
						attachment.getArchiveName()
				);
			}

			csvFilePrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}
}
