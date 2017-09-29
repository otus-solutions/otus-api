package br.org.otus.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriterService {

	private static final String RECORD_SEPARATOR = "\n";

	private CSVFormat csvFileFormat;
	private CSVPrinter csvFilePrinter;
	private ByteArrayOutputStream out;

	public CsvWriterService() {
		try {
			out = new ByteArrayOutputStream();
			csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(RECORD_SEPARATOR);
			csvFilePrinter = new CSVPrinter(new PrintWriter(out), csvFileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeHeader(Iterable<String> headers) {
		try {
			csvFilePrinter.printRecord(headers);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				csvFilePrinter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void writeValues(Iterable<Object> values) {
		try {
			csvFilePrinter.printRecord(values);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				csvFilePrinter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public byte[] getResult() {
		return out.toByteArray();
	}

}
