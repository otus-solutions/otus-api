package org.ccem.otus.model.monitoring.laboratory;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class OrphanExamsProgressDTO {

    ArrayList<OrphanExam> OrphanExamsProgress;

    private class OrphanExam {
        private String title;
        private Integer orphans;
    }

    public static String serialize(OrphanExamsProgressDTO orphanExamsProgressDTO) {
        return OrphanExamsProgressDTO.getGsonBuilder().create().toJson(orphanExamsProgressDTO);
    }

    public static OrphanExamsProgressDTO deserialize(String orphanExamsProgressJson) {
        return OrphanExamsProgressDTO.getGsonBuilder().create().fromJson(orphanExamsProgressJson, OrphanExamsProgressDTO.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
}
