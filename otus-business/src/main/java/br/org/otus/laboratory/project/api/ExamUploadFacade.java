package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.business.ExamResultService;
import br.org.otus.laboratory.project.exam.upload.business.ExamUploadService;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadFacade {

    @Inject
    private ExamUploadService examUploadService;

    @Inject
    private ExamResultService examResultService;

    public ExamResultLot create(ExamUploadDTO examUploadDTO){
        examResultService.create(examUploadDTO.getExamResults());
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
