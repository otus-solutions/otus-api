package org.ccem.otus.model.monitoring.laboratory;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;

public class LaboratoryProgressDTO {

    ArrayList<OrphanExam> orphanExamsProgress;
    private ArrayList<AliquotStats> quantitativeByTypeOfAliquots;
    private ArrayList<PendingResults> pendingResultsByAliquot;
    ArrayList<StorageData> storageByAliquot;
    ArrayList<ExamData> examsQuantitative;
    ArrayList<PendingAliquotCsv> pendingAliquotsCsvData;
    ArrayList<OrphanExamCsv> orphanExamsCsvData;

    public void concatReceivedToPendingResults(LaboratoryProgressDTO received) {
        HashSet<PendingResults> receivedResultsTitleNotInPendingResultsByAliquot = new HashSet<>();
        for (PendingResults pendingResult : this.pendingResultsByAliquot) {
            Integer examsReceived = 0;
            if (received.pendingResultsByAliquot != null) {
                for (PendingResults receivedPendingResults : received.pendingResultsByAliquot) {
                    if(!titleExistInPendingResultsByAliquot(receivedPendingResults.title)){
                        receivedPendingResults.waiting = 0;
                        receivedResultsTitleNotInPendingResultsByAliquot.add(receivedPendingResults);
                    }
                    if (receivedPendingResults.title.equals(pendingResult.title)) {
                        examsReceived = receivedPendingResults.received;
                    }
                }
            }
            pendingResult.received = examsReceived;
        }
        this.pendingResultsByAliquot.addAll(receivedResultsTitleNotInPendingResultsByAliquot);
    }

    private Boolean titleExistInPendingResultsByAliquot(String title){
        Boolean found = false;
        for (PendingResults pendingResult : this.pendingResultsByAliquot) {
            if (pendingResult.title.equals(title)){
                found = true;
                break;
            }
        }
        return found;
    }

    public void fillEmptyWaitingToPendingResults() {
        for (PendingResults pendingResult : this.pendingResultsByAliquot) {
            pendingResult.waiting = 0;
        }
    }

    public void concatReceivedToAliquotStats(LaboratoryProgressDTO received) {
        for (AliquotStats aliquotStats : this.quantitativeByTypeOfAliquots) {
            Integer examsReceived = 0;
            if (received.quantitativeByTypeOfAliquots != null) {
                for (AliquotStats receivedAliquotStats : received.quantitativeByTypeOfAliquots) {
                    if (receivedAliquotStats.title.equals(aliquotStats.title)) {
                        examsReceived = receivedAliquotStats.received;
                    }
                }
            }
            aliquotStats.received = examsReceived;
        }
    }



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
