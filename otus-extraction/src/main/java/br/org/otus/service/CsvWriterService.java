package br.org.otus.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriterService {

	private static final String RECORD_SEPARATOR = "\n";

	private FileWriter fileWriter;
	private CSVFormat csvFileFormat;
	private CSVPrinter csvFilePrinter;

	private void createFileWriter(File file) {
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(File file, ExtractionHeaderService headers, ExtractionValueService values) {
		try {
			createFileWriter(file);
			csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(RECORD_SEPARATOR);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(headers.getHeader());
			csvFilePrinter.printRecord(values.getRecords());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
