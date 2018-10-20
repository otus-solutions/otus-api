package br.org.otus.api;

import br.org.otus.service.CsvWriter;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;

@Stateless
public class ExtractionServiceBean implements ExtractionService{

	@Override
	public byte[] createExtraction(Extractable extractionInterface) throws DataNotFoundException {
    CsvWriter csvWriter = new CsvWriter();
    csvWriter.write(extractionInterface.getHeaders(), extractionInterface.getValues());
		return csvWriter.getResult();
	}
}
