package br.org.otus.laboratory.project.exam.upload;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.List;

public class ExamUploadDTO {
    private ExamResultLot examResultLot;
    private List<ExamResult> examResults;

    public ExamResultLot getExamResultLot() {
        return examResultLot;
    }

    public List<ExamResult> getExamResults() {
        return examResults;
    }

    public static String serialize(ExamUploadDTO examUploadDTO) {
        return ExamUploadDTO.getGsonBuilder().create().toJson(examUploadDTO);
    }

    public static ExamUploadDTO deserialize(String examUploadJson) {
        ExamUploadDTO examUploadDTO = ExamUploadDTO.getGsonBuilder().create().fromJson(examUploadJson, ExamUploadDTO.class);
        //set same fieldCenter for all results
        examUploadDTO.getExamResults()
                .stream()
                .forEach(examResult ->
                        examResult.setFieldCenter(examUploadDTO.getExamResultLot().getFieldCenter()));

        return examUploadDTO;
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder();
    }
}
