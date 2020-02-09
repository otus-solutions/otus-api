package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.project.exam.examLot.businnes.ExamLotService;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotAliquotFilterDTO;
import br.org.otus.response.exception.HttpResponseException;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;


import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@RunWith(PowerMockRunner.class)
public class ExamLotFacadeTest {

  private static final String AEXAM_LOT_OID_STRING = "5a5650501b1ec1199d298f78";
  private static final ObjectId AEXAM_LOT_OID = new ObjectId("5a5650501b1ec1199d298f78");
  private static final Aliquot ALIQUOT = Aliquot.deserialize("{code:1221654877,tubeCode:\"200000\"}");

  @InjectMocks
  private ExamLotFacade examLotFacade;
  @Mock
  private ExamLotAliquotFilterDTO examLotAliquotFilterDTO;
  @Mock
  private ExamLotService examLotService;
  @Mock
  private AliquotService aliquotService;


  @Test
  public void getAliquot_Method_should_return_aliquot() throws ValidationException, DataNotFoundException {
    when(examLotService.validateNewAliquot(examLotAliquotFilterDTO)).thenReturn(ALIQUOT);
    examLotFacade.getAliquot(examLotAliquotFilterDTO);
    verify(examLotService, times(1)).validateNewAliquot(examLotAliquotFilterDTO);
  }

  @Test(expected = HttpResponseException.class)
  public void createMethod_should_capture_ValidationException() throws ValidationException, DataNotFoundException {
    ValidationException e = PowerMockito.spy(new ValidationException());
    doNothing().when(e).printStackTrace();
    doThrow(e).when(examLotService).validateNewAliquot(examLotAliquotFilterDTO);
    examLotFacade.getAliquot(examLotAliquotFilterDTO);
  }

  @Test(expected = HttpResponseException.class)
  public void createMethod_should_capture_DataNotFoundException() throws ValidationException, DataNotFoundException {
    DataNotFoundException e = PowerMockito.spy(new DataNotFoundException());
    doNothing().when(e).printStackTrace();
    doThrow(e).when(examLotService).validateNewAliquot(examLotAliquotFilterDTO);
    examLotFacade.getAliquot(examLotAliquotFilterDTO);
  }

  @Test
  public void getAliquots_Method_should_return_aliquot_list() throws ValidationException, DataNotFoundException {
    ArrayList<Aliquot> aliquotList = new ArrayList<>();
    aliquotList.add(ALIQUOT);
    when(aliquotService.getExamLotAliquots(AEXAM_LOT_OID)).thenReturn(aliquotList);
    examLotFacade.getAliquots(AEXAM_LOT_OID_STRING);
    verify(aliquotService, times(1)).getExamLotAliquots(AEXAM_LOT_OID);
  }
}
