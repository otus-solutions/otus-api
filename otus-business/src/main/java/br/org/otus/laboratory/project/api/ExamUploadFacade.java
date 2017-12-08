package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.exam.upload.Exam;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.business.ExamResultService;
import br.org.otus.laboratory.project.exam.upload.business.ExamService;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadFacade {

    @Inject
    private ExamService examService;

    @Inject
    private ExamResultService examResultService;

    public Exam create(ExamUploadDTO examUploadDTO){
        examResultService.create(examUploadDTO.getExamResults());
        examService.create(examUploadDTO.getExam());
        return null;
    }

    public List<Exam> list(){
        return null;
    }
    public Exam getByID(String id){
        return null;
    }
    public void delete(){
    }
}
