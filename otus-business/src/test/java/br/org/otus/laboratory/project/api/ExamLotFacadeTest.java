package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.businnes.ExamLotService;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

//import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
//@PrepareForTest(ExamLotService.class)
public class ExamLotFacadeTest {
    private static final String USER_EMAIL = "otus@otus.com";

    @InjectMocks
    private ExamLotFacade examLotFacade;
    @Mock
    private ExamLotService examLotService;
    @Mock
    private AliquotService aliquotService;
    @Mock
    private LaboratoryProjectService laboratoryProjectService;
    @Mock
    private ExamLot examLot;

    @Test
    public void createMethod_should_invoke_create_of_examLotService() throws ValidationException, DataNotFoundException {
        examLotFacade.create(examLot, USER_EMAIL);
        verify(examLotService, times(1)).create(examLot, USER_EMAIL);
    }

    @Test(expected = HttpResponseException.class)
    public void createMethod_should_capture_ValidationException() throws ValidationException, DataNotFoundException {
        ValidationException e = PowerMockito.spy(new ValidationException());
        doNothing().when(e).printStackTrace();
        PowerMockito.doThrow(e).when(examLotService).create(examLot, USER_EMAIL);
        examLotFacade.create(examLot, USER_EMAIL);
    }

    @Test(expected = HttpResponseException.class)
    public void createMethod_should_capture_DataNotFoundException() throws ValidationException, DataNotFoundException {
        DataNotFoundException e = PowerMockito.spy(new DataNotFoundException());
        doNothing().when(e).printStackTrace();
        PowerMockito.doThrow(e).when(examLotService).create(examLot, USER_EMAIL);
        examLotFacade.create(examLot, USER_EMAIL);
    }



    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getLots() {
    }

    @Test
    public void getAvailableExams() {
    }
}