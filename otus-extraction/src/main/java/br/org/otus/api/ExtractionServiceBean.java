package br.org.otus.api;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.service.CsvWriterService;

@Stateless
public class ExtractionServiceBean implements ExtractionService{

	@Inject
	private CsvWriterService csvWriterService;

	@Override
	public byte[] createExtraction(Extractable extractionInterface) throws DataNotFoundException {
		csvWriterService.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriterService.getResult();
	}
}
