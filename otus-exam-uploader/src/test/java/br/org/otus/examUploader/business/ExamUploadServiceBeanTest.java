package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class ExamUploadServiceBeanTest {

    private static final String HASH = "58c83f502226685b94f8973a";

    @InjectMocks
    private ExamUploadServiceBean service;

    @Mock
    private ExamResultLotDao examResultLotDAO;

    @Mock
    private ExamResultDao examResultDAO;

    @Mock
    private ArrayList<ExamResult> examResults;

    @Mock
    private ExamResultLot examResultLot;

    @Mock
    private ExamUploadDTO examUploadDTO;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Ignore
    public void method_create_should_not_persist_exam_results_when_lot_persistance_fails() throws ValidationException, DataNotFoundException {
//        Mockito.when(examResultLotDAO.insert(examResultLot)).thenThrow(new RuntimeException());
//        service.create(examUploadDTO, "");
//        Mockito.verify(examResultDAO, Mockito.never());
    }

    @Test
    public void delete() {
    }
}