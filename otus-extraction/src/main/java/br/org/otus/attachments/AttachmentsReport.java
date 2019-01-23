package br.org.otus.attachments;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class AttachmentsReport {

    private List<Attachment> attachmentsList;

    public List<Attachment> getAttachmentsList() {
        return attachmentsList;
    }

    public void concat(List<Attachment> attachmentsList) {
        this.attachmentsList.addAll(attachmentsList);
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
