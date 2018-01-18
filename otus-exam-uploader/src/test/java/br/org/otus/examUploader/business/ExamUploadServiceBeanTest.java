package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.aliquot.AliquotCollectionData;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Matchers.eq;

@RunWith(PowerMockRunner.class)
public class ExamUploadServiceBeanTest {

    private static String EMAIL_STRING = "fulano@detal.com";

    @InjectMocks
    private ExamUploadServiceBean service;

    @Mock
    LaboratoryProjectService laboratoryProjectService;

    @Mock
    private ExamResultLotDao examResultLotDAO;

    @Mock
    private ExamResultDao examResultDAO;

    @Mock
    private ExamUploadDTO examUploadDTO;

    @Mock
    private ExamResult examResult;

    @Mock
    private ExamResultLot examResultLot;


    @Test
    public void create_should_not_persist_when_validation_fails() throws ValidationException, DataNotFoundException {
         service = PowerMockito.spy(new ExamUploadServiceBean());

        List<ExamResult> examResults = new ArrayList<>();
        examResults.add(examResult);
        PowerMockito.when(examUploadDTO.getExamResults()).thenReturn(examResults);

        Mockito.doThrow(new ValidationException()).when(service).validateExamResults(Mockito.any());

        try{
            service.create(examUploadDTO, EMAIL_STRING);
        }catch (ValidationException ignored){

        }finally{
            Mockito.verify(examResultLotDAO, Mockito.times(0)).insert(examResultLot);
            Mockito.verify(examResultDAO, Mockito.times(0)).insertMany(Mockito.any());
        }

    }

    @Test
    //TODO 17/01/18: review deletion rule
    public void delete_should_not_delete_a_lot_if_doesnt_find_associated_exam_results() throws DataNotFoundException {
        Mockito.doThrow(new DataNotFoundException()).when(examResultDAO).deleteByExamId(Mockito.any());
        try{
            service.delete(Mockito.any());
        }catch (DataNotFoundException ignored){

        }finally {
            Mockito.verify(examResultLotDAO, Mockito.times(0)).deleteById(Mockito.any());
        }

    }

    @Test
    public void validateExamResults_should_not_throw_ValidationException_when_resultsToVerify_is_subSet_of_allAliquots() throws DataNotFoundException, ValidationException {
        List<ExamResult> resultsToVerify = new ArrayList<>(2);
        List<WorkAliquot> allAliquots = new ArrayList<>(10);

        IntStream.range(1,2).forEach(counter -> {
            ExamResult examResult = new ExamResult();
            examResult.setAliquotCode(String.valueOf(counter));
            resultsToVerify.add(examResult);
        });

        IntStream.range(1,11).forEach(counter -> {
            WorkAliquot mock = Mockito.mock(WorkAliquot.class);
            Mockito.when(mock.getCode()).thenReturn(String.valueOf(counter));
            allAliquots.add(mock);
        });

        PowerMockito.when(examUploadDTO.getExamResults()).thenReturn(resultsToVerify);
        PowerMockito.when(laboratoryProjectService.getAllAliquots()).thenReturn(allAliquots);

        service.validateExamResults(examUploadDTO);
    }

    @Test (expected = ValidationException.class)
    public void validateExamResults_should_throw_ValidationException_when_resultsToVerify_is_not_subSet_of_allAliquots() throws DataNotFoundException, ValidationException {
        List<ExamResult> resultsToVerify= new ArrayList<>();

        ExamResult examResult = new ExamResult();
        examResult.setAliquotCode("a");

        resultsToVerify.add(examResult);

        PowerMockito.when(examUploadDTO.getExamResults()).thenReturn(resultsToVerify);
        PowerMockito.when(laboratoryProjectService.getAllAliquots()).thenReturn(new ArrayList<>());
        service.validateExamResults(examUploadDTO);

    }
}