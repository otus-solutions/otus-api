package br.org.otus.laboratory.project.exam.upload;

import com.google.gson.GsonBuilder;

import java.util.List;

public class ExamUploadDTO {
    private Exam exam;
    private List<ExamResult> examResults;

    public Exam getExam() {
        return exam;
    }

    public List<ExamResult> getExamResults() {
        return examResults;
    }

    public static String serialize(ExamUploadDTO examUploadDTO) {
        return ExamUploadDTO.getGsonBuilder().create().toJson(examUploadDTO);
    }

    public static ExamUploadDTO deserialize(String examUploadDTO) {
        return ExamUploadDTO.getGsonBuilder().create().fromJson(examUploadDTO, ExamUploadDTO.class);
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder();
    }
}
