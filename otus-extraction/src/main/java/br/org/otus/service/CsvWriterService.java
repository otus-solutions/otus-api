package br.org.otus.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriterService {

	private static final String RECORD_SEPARATOR = "\n";

	private FileWriter fileWriter;
	private CSVFormat csvFileFormat;
	private CSVPrinter csvFilePrinter;

	public CsvWriterService(File file) {
		try {
			fileWriter = new FileWriter(file);
			csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(RECORD_SEPARATOR);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeHeader(File file, List<Object> headers) {
		try {
			csvFilePrinter.printRecord(headers);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void writeValues(File file, List<Object> values) {
		try {
			csvFilePrinter.printRecord(values);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

}
