package br.org.otus.attachments;

import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AttachmentsReport {

  private List<Attachment> attachmentsList = new ArrayList<>();

  public List<Attachment> getAttachmentsList() {
    return attachmentsList;
  }

  public void concat(List<Attachment> attachmentsList) {
    this.attachmentsList.addAll(attachmentsList);
  }

  public byte[] getCsv() {
    char DELIMITER = ';';
    String RECORD_SEPARATOR = "\n";

    CSVFormat csvFileFormat;
    CSVPrinter csvFilePrinter;

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      csvFileFormat = CSVFormat.newFormat(DELIMITER).withRecordSeparator(RECORD_SEPARATOR).withQuote('\"').withQuoteMode(QuoteMode.MINIMAL);
      csvFilePrinter = new CSVPrinter(new PrintWriter(out), csvFileFormat);

      csvFilePrinter.printRecord(
        "Numero de Recrutamento",
        "ID da Questão",
        "ID do Arquivo",
        "Nome do Arquivo",
        "Status",
        "Data de Upload",
        "ExternalID"
      );

      for (AttachmentsReport.Attachment attachment : this.attachmentsList) {
        csvFilePrinter.printRecord(
          attachment.getRecruitmentNumber(),
          attachment.getQuestionId(),
          attachment.getArchiveId(),
          attachment.getArchiveName(),
          attachment.getTranslatedStatus(),
          attachment.getUploadDate(),
          attachment.getExternalID()
        );
      }

      csvFilePrinter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return out.toByteArray();
  }

  public class Attachment {
    private Integer recruitmentNumber;
    private String questionId;
    private String archiveId;
    private String status;
    private String archiveName;
    private String uploadDate;
    private String externalID;

    public Integer getRecruitmentNumber() {
      return recruitmentNumber;
    }

    public String getQuestionId() {
      return questionId;
    }

    public String getArchiveId() {
      return archiveId;
    }

    public String getStatus() {
      return status;
    }

    public String getArchiveName() {
      return archiveName;
    }

    public String getUploadDate() {
      return uploadDate;
    }

    public String getExternalID() {
      return externalID;
    }

    public String getTranslatedStatus() {
      String translatedStatus = "";
      if (status.equals("Stored")) {
        translatedStatus = "Armazenado";
      } else if (status.equals("Removed")) {
        translatedStatus = "Removido";
      }
      return translatedStatus;
    }
  }

  public static AttachmentsReport deserialize(String Report) {
    GsonBuilder builder = getGsonBuilder();
    return builder.create().fromJson(Report, AttachmentsReport.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

}
