package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.business.ExamUploadService;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadFacade {

    @Inject
    private ExamUploadService examUploadService;

    public ExamResultLot create(String examUploadJson){
        ExamUploadDTO examUploadDTO = ExamUploadDTO.deserialize(examUploadJson);
        examUploadService.create(examUploadDTO);
        return null;
    }

    public List<ExamResultLot> list(){
        return null;
    }
    public ExamResultLot getByID(String id){
        return null;
    }
    public void delete(){
    }
}
