package service;

import model.Stage;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import persistence.StageDao;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class StageServiceBeanTest {

  private static final ObjectId STAGE_OID = new ObjectId("5f77920624439758ce4a43ab");
  private static final String ACRONYM = "ABC";

  @InjectMocks
  private StageServiceBean stageServiceBean;

  @Mock
  private StageDao stageDao;

  private Stage stage;

  @Before
  public void setUp(){
    stage = new Stage();
    stage.setId(STAGE_OID);
  }

  @Test
  public void create_method_should_call_dao_create_method() throws AlreadyExistException {
    stageServiceBean.create(stage);
    verify(stageDao, Mockito.times(1)).create(stage);
  }

  @Test(expected = AlreadyExistException.class)
  public void create_method_should_throw_call_AlreadyExistException() throws Exception {
    PowerMockito.doThrow(new AlreadyExistException()).when(stageDao, "create", stage);
    stageServiceBean.create(stage);
  }

  @Test
  public void update_method_should_call_dao_update_method() throws DataNotFoundException, AlreadyExistException {
    stageServiceBean.update(stage);
    verify(stageDao, Mockito.times(1)).update(stage);
  }

  @Test(expected = AlreadyExistException.class)
  public void update_method_should_throw_call_AlreadyExistException() throws Exception {
    PowerMockito.doThrow(new AlreadyExistException()).when(stageDao, "update", stage);
    stageServiceBean.update(stage);
  }

  @Test
  public void delete_method_should_call_dao_delete_method() throws DataNotFoundException {
    stageServiceBean.delete(STAGE_OID);
    verify(stageDao, Mockito.times(1)).delete(STAGE_OID);
  }

  @Test
  public void getByID_method_should_call_dao_getByID_method() throws DataNotFoundException {
    stageServiceBean.getByID(STAGE_OID);
    verify(stageDao, Mockito.times(1)).getByID(STAGE_OID);
  }

  @Test
  public void getAll_method_should_call_dao_getAll_method() throws MemoryExcededException {
    stageServiceBean.getAll();
    verify(stageDao, Mockito.times(1)).getAll();
  }

  @Test
  public void updateSurveyAcronymsOfStage_method_should_call_dao_getAll_method() throws DataNotFoundException {
    stageServiceBean.updateSurveyAcronymsOfStage(stage);
    verify(stageDao, Mockito.times(1)).updateSurveyAcronymsOfStage(stage);
  }

  @Test
  public void updateStagesOfSurveyAcronym_method_should_call_dao_getAll_method() throws DataNotFoundException {
    List<ObjectId> stageOIDsToAdd = new ArrayList<>();
    List<ObjectId> stageOIDsToRemove = new ArrayList<>();
    stageServiceBean.updateStagesOfSurveyAcronym(ACRONYM, stageOIDsToAdd, stageOIDsToRemove);
    verify(stageDao, Mockito.times(1)).updateStagesOfSurveyAcronym(ACRONYM, stageOIDsToAdd, stageOIDsToRemove);
  }

}
