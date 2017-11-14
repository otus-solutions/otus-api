package br.org.otus.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

public class CsvWriterService {

	private static final char DELIMITER = ';';
	private static final String RECORD_SEPARATOR = "\n";

	private CSVFormat csvFileFormat;
	private CSVPrinter csvFilePrinter;
	private ByteArrayOutputStream out;

	public CsvWriterService() {
		try {
			out = new ByteArrayOutputStream();
			csvFileFormat = CSVFormat.newFormat(DELIMITER).withRecordSeparator(RECORD_SEPARATOR).withQuote('\"').withQuoteMode(QuoteMode.ALL);
			csvFilePrinter = new CSVPrinter(new PrintWriter(out), csvFileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(Iterable<String> headers, List<List<Object>> values) {
		try {
			csvFilePrinter.printRecord(headers);
			for (List<Object> record : values) {
				csvFilePrinter.printRecord(record);
			}
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

	public byte[] getResult() {
		return out.toByteArray();
	}

}
