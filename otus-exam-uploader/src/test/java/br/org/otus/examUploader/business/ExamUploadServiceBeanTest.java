package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create() throws ValidationException, DataNotFoundException {
        List<ExamResult> examResults = new ArrayList<>();
        examResults.add(examResult);
        PowerMockito.when(examUploadDTO.getExamResults()).thenReturn(examResults);
        PowerMockito.when(service.validateExamResults())
        String email_string = service.create(examUploadDTO, "EMAIL_STRING");

    }

    @Test
    public void delete() {
    }
}