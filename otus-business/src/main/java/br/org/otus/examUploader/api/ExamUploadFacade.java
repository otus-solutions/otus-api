package br.org.otus.examUploader.api;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.business.ExamUploadService;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

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
        return examUploadService.list();
    }

    public ExamResultLot getById(String id){
        return examUploadService.getByID(id);
    }

    public void deleteById(String id){
        try {
            examUploadService.delete(id);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ExamResult> listResults(String id){
        ObjectId objectId = new ObjectId(id);
        return examUploadService.getAllByExamId(objectId);

    }
}
