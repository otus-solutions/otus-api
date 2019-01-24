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
                    "recruitmentNumber",
                    "questionId",
                    "archiveId",
                    "status",
                    "status"
            );

            for(AttachmentsReport.Attachment attachment : this.attachmentsList){
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

    public class Attachment {
        private Integer recruitmentNumber;
        private String questionId;
        private String archiveId;
        private String status;
        private String archiveName;

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
