package br.org.otus.laboratory.participant.aliquot.business;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.businnes.ExamLotServiceBean;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class AliquotServiceBeanTest {
  private static final ObjectId OBJECT_ID = new ObjectId();
  private static final String ALIQUOT_CODE = "2131244534";
  private static final Long RECRUITMENT_NUMBER = (long) 1063154;

  @InjectMocks
  private AliquotServiceBean aliquotServiceBean = PowerMockito.spy(new AliquotServiceBean());

  @Mock
  private AliquotDao aliquotDao;

  @Mock
  private TransportationAliquotFiltersDTO transportationAliquotFiltersDTO;

  @Mock
  private ExamLotDao examLotDao;

  @Mock
  private ExamLot examLot;

  @Mock
  private Aliquot aliquot;

  @Mock
  private List<Aliquot> aliquotList;

  @Test
  public void getAliquots_method_should_call_aliquotDao_getAliquots() {
    aliquotServiceBean.getAliquots();
    Mockito.verify(aliquotDao, Mockito.times(1)).getAliquots();
  }

  @Test
  public void getAliquots_method_should_return_aliquotList() {
    when(aliquotDao.getAliquots()).thenReturn(aliquotList);
    assertEquals(aliquotList, aliquotServiceBean.getAliquots());
  }

  @Test
  public void getAliquots_by_rn_method_should_call_aliquotDao_list_by_rn() {
    aliquotServiceBean.getAliquots(RECRUITMENT_NUMBER);
    Mockito.verify(aliquotDao, Mockito.times(1)).list(RECRUITMENT_NUMBER);
  }

  @Test
  public void getAliquots_by_rn_method_should_return_aliquotList() {
    when(aliquotDao.list(RECRUITMENT_NUMBER)).thenReturn(aliquotList);
    assertEquals(aliquotList, aliquotServiceBean.getAliquots(RECRUITMENT_NUMBER));
  }

  @Test(expected = ValidationException.class)
  public void getAliquot_by_transportationAliquotFiltersDTO_method_should_throw_ValidationException() throws DataNotFoundException, ValidationException {
    when(aliquotDao.getAliquot(transportationAliquotFiltersDTO)).thenReturn(aliquot);
    when(aliquot.getTransportationLotId()).thenReturn(OBJECT_ID);
    aliquotServiceBean.getAliquot(transportationAliquotFiltersDTO);
  }

  @Test
  public void getAliquot_by_transportationAliquotFiltersDTO_method_should_call_aliquotDao_getAliquot_by_transportationAliquotFiltersDTO() throws DataNotFoundException, ValidationException {
    when(aliquotDao.getAliquot(transportationAliquotFiltersDTO)).thenReturn(aliquot);
    aliquotServiceBean.getAliquot(transportationAliquotFiltersDTO);
    Mockito.verify(aliquotDao, Mockito.times(1)).getAliquot(transportationAliquotFiltersDTO);
  }

  @Test
  public void getAliquot_by_transportationAliquotFiltersDTO_method_should_return_aliquot() throws ValidationException, DataNotFoundException {
    when(aliquotDao.getAliquot(transportationAliquotFiltersDTO)).thenReturn(aliquot);
    assertEquals(aliquot, aliquotServiceBean.getAliquot(transportationAliquotFiltersDTO));
  }

  @Test
  public void find_by_code_method_should_call_aliquotDao_find_by_code() throws DataNotFoundException {
    aliquotServiceBean.find(ALIQUOT_CODE);
    Mockito.verify(aliquotDao, Mockito.times(1)).find(ALIQUOT_CODE);
  }

  @Test
  public void find_by_code_method_should_return_aliquot() throws DataNotFoundException {
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    assertEquals(aliquot, aliquotServiceBean.find(ALIQUOT_CODE));
  }

  @Test
  public void getAliquotsByPeriod_method_should_call_aliquotDao_getAliquotsByPeriod() throws DataNotFoundException {
    aliquotServiceBean.getAliquotsByPeriod(transportationAliquotFiltersDTO);
    Mockito.verify(aliquotDao, Mockito.times(1)).getAliquotsByPeriod(transportationAliquotFiltersDTO);
  }

  @Test
  public void getAliquotsByPeriod_method_should_return_aliquotList() throws DataNotFoundException {
    when(aliquotDao.getAliquotsByPeriod(transportationAliquotFiltersDTO)).thenReturn(aliquotList);
    assertEquals(aliquotList, aliquotServiceBean.getAliquotsByPeriod(transportationAliquotFiltersDTO));
  }

  @Test
  public void exists_method_should_call_aliquotDao_exists() {
    aliquotServiceBean.exists(ALIQUOT_CODE);
    Mockito.verify(aliquotDao, Mockito.times(1)).exists(ALIQUOT_CODE);
  }

  @Test
  public void getExamLotAliquots_method_should_call_aliquotDao_getExamLotAliquots() throws DataNotFoundException {
    aliquotServiceBean.getExamLotAliquots(OBJECT_ID);
    Mockito.verify(aliquotDao, Mockito.times(1)).getExamLotAliquots(OBJECT_ID);
  }

  @Test
  public void getExamLotAliquots_method_should_return_aliquotList() throws DataNotFoundException {
    when(aliquotDao.getExamLotAliquots(OBJECT_ID)).thenReturn(aliquotList);
    assertEquals(aliquotList, aliquotServiceBean.getExamLotAliquots(OBJECT_ID));
  }


}
