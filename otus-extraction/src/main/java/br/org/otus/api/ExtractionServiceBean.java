package br.org.otus.api;

import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.persistence.AttachmentsExtractionDao;
import br.org.otus.service.CsvWriter;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ExtractionServiceBean implements ExtractionService {

	@Inject
	private AttachmentsExtractionDao attachmentsExtractionDao;

	@Override
	public byte[] createExtraction(Extractable extractionInterface) throws DataNotFoundException {
    		CsvWriter csvWriter = new CsvWriter();
    		csvWriter.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriter.getResult();
	}

	@Override
	public byte[] getAttachmentsReport(String acronym,Integer version) throws DataNotFoundException {
		AttachmentsReport attachmentsReport = attachmentsExtractionDao.fetchAttachmentsReport(acronym, version);
		return attachmentsReport.getCsv();
	}
}
