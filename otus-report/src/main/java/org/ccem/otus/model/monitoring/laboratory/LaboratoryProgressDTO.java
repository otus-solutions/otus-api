package org.ccem.otus.model.monitoring.laboratory;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class LaboratoryProgressDTO {

    ArrayList<OrphanExam> orphanExamsProgress;
    ArrayList<AliquotStats> quantitativeByTypeOfAliquots;
    ArrayList<PendingResults> pendingResultsByAliquot;
    ArrayList<StorageData> storageByAliquot;
    ArrayList<ExamData> examsQuantitative;
    ArrayList<PendingAliquotCsv> pendingAliquotsCsvData;
    ArrayList<OrphanExamCsv> orphanExamsCsvData;

    private class OrphanExam {
        private String title;
        private Integer orphans;

    }
    private class AliquotStats {
        private String title;
        private Integer received;
        private Integer prepared;
        private Integer transported;

    }
    private class PendingResults {
        private String title;
        private Integer received;
        private Integer waiting;


    }
    private class StorageData {
        private String title;
        private Integer storage;

    }
    private class ExamData {
        private String title;
        private Integer exams;

    }

    private class PendingAliquotCsv {
        private String aliquot;
        private Integer transported;
        private Integer prepared;
    }

    private class OrphanExamCsv {
        private String aliquotCode;
        private String examName;
    }

    public static String serialize(LaboratoryProgressDTO laboratoryProgressDTO) {
        return LaboratoryProgressDTO.getGsonBuilder().create().toJson(laboratoryProgressDTO);
    }

    public static LaboratoryProgressDTO deserialize(String laboratoryProgressJson) {
        return LaboratoryProgressDTO.getGsonBuilder().create().fromJson(laboratoryProgressJson, LaboratoryProgressDTO.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
