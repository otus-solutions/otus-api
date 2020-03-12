package br.org.otus.laboratory.project.exam.examLot.businnes;


import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotAliquotFilterDTO;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExamLotServiceBean.class)
public class ExamLotServiceBeanTest {
  private static final String OPERATOR = "test";
  private static final ObjectId OBJECT_ID = new ObjectId();
  private static final String LOT_CODE = "331244534";
  private static final String LOT_CODE_INCREMENT = "331244535";
  private static final String ALIQUOT_CODE = "333244534";
  private static final String EXAM = "exam_lot";
  private static final Integer LAST_INSERTION_CODE = 300000001;

  @InjectMocks
  private ExamLotServiceBean examLotServiceBean = PowerMockito.spy(new ExamLotServiceBean());

  @Mock
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  @Mock
  private AliquotDao aliquotDao;

  @Mock
  private ExamLotDao examLotDao;

  @Mock
  private ExamLot examLot;

  @Mock
  private Aliquot aliquot = Aliquot.deserialize("{name:\"teste\",code:1221654877,tubeCode:\"200000\",fieldCenter:{acronym:\"teste\"}}");

  @Mock
  private ArrayList<Aliquot> aliquotList;

  @Mock
  private ExamLotAliquotFilterDTO examLotAliquotFilterDTO;

  @Mock
  private FieldCenter fieldCenter = FieldCenter.deserialize("{acronym:\"teste\"}");

  @Test
  public void create_should_validate_lot() throws Exception {
    examLotServiceBean.create(examLot, OPERATOR);
    verifyPrivate(examLotServiceBean, Mockito.times(1)).invoke("validateLot", examLot);
  }

  @Test
  public void create_should_set_operator() throws Exception {
    examLotServiceBean.create(examLot, OPERATOR);
    Mockito.verify(examLot, Mockito.times(1)).setOperator(OPERATOR);
  }

  @Test
  public void create_should_set_code() throws Exception {
    when(examLotDao.getLastExamLotCode()).thenReturn(Integer.parseInt(LOT_CODE));
    when(laboratoryConfigurationDao.createNewLotCodeForExam(Integer.parseInt(LOT_CODE))).thenReturn(LOT_CODE_INCREMENT);
    examLotServiceBean.create(examLot, OPERATOR);
    Mockito.verify(examLot, Mockito.times(1)).setCode(LOT_CODE_INCREMENT);
  }

  @Test
  public void create_should_persist_lot() throws Exception {
    when(examLotDao.getLastExamLotCode()).thenReturn(Integer.parseInt(LOT_CODE));
    examLotServiceBean.create(examLot, OPERATOR);
    Mockito.verify(examLotDao, Mockito.times(1)).persist(examLot);
    Mockito.verify(examLotDao, Mockito.times(1)).getLastExamLotCode();
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).createNewLotCodeForExam(Integer.parseInt(LOT_CODE));
  }

  @Test
  public void create_should_add_to_exam_lot() throws Exception {
    aliquotList.add(aliquot);
    examLot.setAliquotList(aliquotList);
    when(examLotDao.persist(examLot)).thenReturn(OBJECT_ID);
    ArrayList<String> codeList = new ArrayList<>();
    codeList.add(LOT_CODE);
    codeList.add(LOT_CODE_INCREMENT);
    when(examLot.getAliquotCodeList()).thenReturn(codeList);
    examLotServiceBean.create(examLot, OPERATOR);
    Mockito.verify(aliquotDao, Mockito.times(1)).updateExamLotId(examLot.getAliquotCodeList(), OBJECT_ID);
  }

  @Test
  public void update_should_validate_lot() throws Exception {
    when(examLot.getCode()).thenReturn(LOT_CODE);
    when(examLotDao.findByCode(LOT_CODE)).thenReturn(examLot);
    examLotServiceBean.update(examLot);
    verifyPrivate(examLotServiceBean, Mockito.times(1)).invoke("validateLot", examLot);
  }

  @Test
  public void update_should_call_updateExamLotId() throws Exception {
    when(examLot.getCode()).thenReturn(LOT_CODE);
    ArrayList<String> codeList = new ArrayList<>();
    codeList.add(LOT_CODE);
    when(examLot.getAliquotCodeList()).thenReturn(codeList);
    when(examLot.getNewAliquotCodeList(codeList)).thenReturn(codeList);
    when(examLot.getLotId()).thenReturn(OBJECT_ID);
    when(examLotDao.findByCode(LOT_CODE)).thenReturn(examLot);
    examLotServiceBean.update(examLot);
    Mockito.verify(aliquotDao, Mockito.times(1)).updateExamLotId(codeList, OBJECT_ID);
  }

  @Test
  public void list_should_call_exam_lot_dao_find() throws Exception {
    examLotServiceBean.list(anyString());
    Mockito.verify(examLotDao, Mockito.times(1)).find(anyString());
  }

  @Test
  public void delete_should_call_updateExamLotId() throws Exception {
    when(examLot.getLotId()).thenReturn(OBJECT_ID);
    when(examLotDao.findByCode(LOT_CODE)).thenReturn(examLot);
    ArrayList<String> codeList = new ArrayList<>();
    when(examLot.getAliquotCodeList()).thenReturn(codeList);
    examLotServiceBean.delete(LOT_CODE);
    Mockito.verify(aliquotDao, Mockito.times(1)).updateExamLotId(codeList, OBJECT_ID);
  }

  @Test
  public void delete_should_call_exam_lot_dao_delete() throws Exception {
    when(examLotDao.getLastExamLotCode()).thenReturn(LAST_INSERTION_CODE);
    when(laboratoryConfigurationDao.getLastInsertion(EXAM)).thenReturn(Integer.parseInt(LOT_CODE));
    when(examLot.getLotId()).thenReturn(OBJECT_ID);
    when(examLotDao.findByCode(LOT_CODE)).thenReturn(examLot);
    ArrayList<String> codeList = new ArrayList<>();
    when(examLot.getAliquotCodeList()).thenReturn(codeList);
    examLotServiceBean.delete(LOT_CODE);
    Mockito.verify(examLotDao, Mockito.times(1)).delete(OBJECT_ID);
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(0)).restoreLotConfiguration(EXAM, Integer.parseInt(LOT_CODE));
    Mockito.verify(examLotDao, Mockito.times(1)).getLastExamLotCode();

  }

  @Test
  public void delete_should_call_exam_lot_dao_delete_with_restore_configuration() throws Exception {
    when(examLotDao.getLastExamLotCode()).thenReturn(Integer.parseInt(LOT_CODE));
    when(laboratoryConfigurationDao.getLastInsertion(EXAM)).thenReturn(LAST_INSERTION_CODE);
    when(examLot.getLotId()).thenReturn(OBJECT_ID);
    when(examLotDao.findByCode(LOT_CODE)).thenReturn(examLot);
    ArrayList<String> codeList = new ArrayList<>();
    when(examLot.getAliquotCodeList()).thenReturn(codeList);
    examLotServiceBean.delete(LOT_CODE);
    Mockito.verify(examLotDao, Mockito.times(1)).delete(OBJECT_ID);
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).restoreLotConfiguration(EXAM, Integer.parseInt(LOT_CODE));
    Mockito.verify(examLotDao, Mockito.times(1)).getLastExamLotCode();

  }

  @Test
  public void validateNewAliquot_should_call_aliquotDao_find_by_code() throws Exception {
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(examLotAliquotFilterDTO.getAliquotCode()).thenReturn(ALIQUOT_CODE);
    when(examLotAliquotFilterDTO.getLotType()).thenReturn("teste");
    when(examLotAliquotFilterDTO.getFieldCenter()).thenReturn(fieldCenter);
    examLotServiceBean.validateNewAliquot(examLotAliquotFilterDTO);
    Mockito.verify(aliquotDao, Mockito.times(1)).find(ALIQUOT_CODE);
  }

  @Test(expected = ValidationException.class)
  public void validateNewAliquot_should_throw_ValidationException_when_type_is_invalid() throws Exception {
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(examLotAliquotFilterDTO.getAliquotCode()).thenReturn(ALIQUOT_CODE);
    when(examLotAliquotFilterDTO.getLotType()).thenReturn("test");
    when(examLotAliquotFilterDTO.getFieldCenter()).thenReturn(fieldCenter);
    examLotServiceBean.validateNewAliquot(examLotAliquotFilterDTO);
    Mockito.verify(aliquotDao, Mockito.times(1)).find(ALIQUOT_CODE);
  }

  @Test(expected = ValidationException.class)
  public void validateNewAliquot_should_throw_ValidationException_when_center_is_invalid() throws Exception {
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(examLotAliquotFilterDTO.getAliquotCode()).thenReturn(ALIQUOT_CODE);
    when(examLotAliquotFilterDTO.getLotType()).thenReturn("teste");
    fieldCenter.setAcronym("SP");
    when(examLotAliquotFilterDTO.getFieldCenter()).thenReturn(fieldCenter);
    examLotServiceBean.validateNewAliquot(examLotAliquotFilterDTO);
  }

  @Test(expected = ValidationException.class)
  public void validateNewAliquot_should_throw_ValidationException_when_is_in_exam_lot() throws Exception {
    aliquot.setExamLotId(OBJECT_ID);
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(examLotAliquotFilterDTO.getAliquotCode()).thenReturn(ALIQUOT_CODE);
    when(examLotAliquotFilterDTO.getLotType()).thenReturn("teste");
    when(examLotAliquotFilterDTO.getFieldCenter()).thenReturn(fieldCenter);
    when(examLotDao.find(OBJECT_ID)).thenReturn(examLot);
    when(examLot.getCode()).thenReturn(LOT_CODE);
    examLotServiceBean.validateNewAliquot(examLotAliquotFilterDTO);
  }
}
