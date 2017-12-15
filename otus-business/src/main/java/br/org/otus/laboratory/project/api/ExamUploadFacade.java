package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.exam.upload.ExamResult;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.business.ExamUploadService;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadFacade {

    @Inject
    private ExamUploadService examUploadService;

    public String create(String examUploadJson){
        ExamUploadDTO examUploadDTO = ExamUploadDTO.deserialize(examUploadJson);
        String lotId = examUploadService.create(examUploadDTO);
        return lotId;
    }

    public List<ExamResultLot> list(){
        return null;
    }
    public ExamResultLot getByID(String id){
        return null;
    }
    public void delete(){
    }

    public List<ExamResult> listResults(String id){
        ObjectId objectId = new ObjectId(id);
        return examUploadService.getAllByExamId(objectId);

    }
}
