package br.org.otus.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

public class CsvWriter {

  private static final char DELIMITER = ';';
  private static final String RECORD_SEPARATOR = "\n";

  private CSVFormat csvFileFormat;
  private CSVPrinter csvFilePrinter;
  private ByteArrayOutputStream out;

  public CsvWriter() {
    try {
      out = new ByteArrayOutputStream();
      csvFileFormat = CSVFormat.newFormat(DELIMITER).withRecordSeparator(RECORD_SEPARATOR).withQuote('\"').withQuoteMode(QuoteMode.MINIMAL);
      csvFilePrinter = new CSVPrinter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")), csvFileFormat);
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
