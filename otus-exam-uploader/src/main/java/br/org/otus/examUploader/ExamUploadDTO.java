package br.org.otus.examUploader;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

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
        return examUploadDTO;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        return builder;
    }
}
