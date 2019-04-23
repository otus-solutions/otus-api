package br.org.otus.laboratory.project.exam.examInapplicability;

import com.google.gson.GsonBuilder;

public class ExamInapplicability {
    private Long recruitmentNumber;
    private String name;
    private String observation;

    public static String serialize(ExamInapplicability reportTemplate) {
        return ExamInapplicability.getGsonBuilder().create().toJson(reportTemplate);
    }

    public static ExamInapplicability deserialize(String reportTemplateJson) {
        ExamInapplicability examInapplicability = ExamInapplicability.getGsonBuilder().create().fromJson(reportTemplateJson, ExamInapplicability.class);
        return examInapplicability;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return builder;
    }

    public Long getRecruitmentNumber() {
        return this.recruitmentNumber;
    }

    public String getName() {
        return name;
    }
}
