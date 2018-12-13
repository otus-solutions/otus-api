package org.ccem.otus.model.monitoring.laboratory;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedList;

public class LaboratoryProgressDTO {

    ArrayList<OrphanExam> orphanExamsProgress;
    ArrayList<AliquotStats> quantitativeByTypeOfAliquots;
    ArrayList<PendingResults> pendingResultsByAliquot;
    ArrayList<StorageData> storageByAliquot;
    ArrayList<ExamData> examsQuantitative;
    ArrayList<PendingAliquot> pendingAliquotsCsvData;
    OrphanExamsCsvData orphanExamsCsvData;

    private class OrphanExam {
        private String title;
        private Integer orphans;

    }
    private class AliquotStats {
        private String title;
        private Integer transported;
        private Integer prepared;
        private Integer received;

    }
    private class PendingResults {
        private String title;
        private Integer waiting;
        private Integer received;


    }
    private class StorageData {
        private String title;
        private Integer storage;

    }
    private class ExamData {
        private String title;
        private Integer exams;

    }

    private class PendingAliquot {
        private String aliquot;
        private Integer transported;
        private Integer prepared;
    }

    private class OrphanExamsCsvData {
        private LinkedList<String> aliquots;
        private LinkedList<String> examNames;
    }

    public static String serialize(LaboratoryProgressDTO orphanExamsProgressDTO) {
        return LaboratoryProgressDTO.getGsonBuilder().create().toJson(orphanExamsProgressDTO);
    }

    public static LaboratoryProgressDTO deserialize(String orphanExamsProgressJson) {
        return LaboratoryProgressDTO.getGsonBuilder().create().fromJson(orphanExamsProgressJson, LaboratoryProgressDTO.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
