package org.ccem.otus.model.monitoring.laboratory;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class LaboratoryProgressDTO {

    ArrayList<OrphanExam> orphanExamsProgress;
    ArrayList<AliquotStats> quantitativeByTypeOfAliquots;

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
