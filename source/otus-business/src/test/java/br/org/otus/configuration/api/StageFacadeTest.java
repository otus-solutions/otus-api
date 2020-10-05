package br.org.otus.configuration.api;

import br.org.otus.response.exception.HttpResponseException;
import model.Stage;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import service.StageService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Stage.class})
public class StageFacadeTest {

  private static final String STAGE_ID = "5f77920624439758ce4a43ab";
  private static final ObjectId STAGE_OID = new ObjectId(STAGE_ID);
  private static final String STAGE_JSON = "{}";

  @InjectMocks
  private StageFacade stageFacade;

  @Mock
  private StageService stageService;
  @Mock
  private ActivityService activityService;

  private Stage stage;

  @Before
  public void setUp(){
    stage = new Stage();
    stage.setId(STAGE_OID);

    PowerMockito.mockStatic(Stage.class);
    when(Stage.deserialize(STAGE_JSON)).thenReturn(stage);
  }

  @Test
  public void create_method_should_call_stageService_create_method(){
    when(stageService.create(stage)).thenReturn(STAGE_OID);
    String result = stageFacade.create(STAGE_JSON);
    verify(stageService, Mockito.times(1)).create(stage);
    assertEquals(STAGE_ID, result);
  }

  @Test
  public void update_method_should_call_stageService_update_method() throws DataNotFoundException {
    stageFacade.update(STAGE_ID, STAGE_JSON);
    verify(stageService, Mockito.times(1)).update(stage);
  }

  @Test(expected = HttpResponseException.class)
  public void update_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(new DataNotFoundException()).when(stageService, "update", stage);
    stageFacade.update(STAGE_ID, STAGE_JSON);
  }


  @Test
  public void delete_method_should_call_stageService_delete_and_activityService_removeStageFromActivities_methods() throws DataNotFoundException {
    stageFacade.delete(STAGE_ID);
    verify(stageService, Mockito.times(1)).delete(STAGE_OID);
    verify(activityService, Mockito.times(1)).removeStageFromActivities(STAGE_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void delete_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(new DataNotFoundException()).when(stageService, "delete", STAGE_OID);
    stageFacade.delete(STAGE_ID);
  }


  @Test
  public void getByID_method_should_call_stageService_getByID_method() throws DataNotFoundException {
    stageFacade.getByID(STAGE_ID);
    verify(stageService, Mockito.times(1)).getByID(STAGE_OID);
  }

  @Test(expected = HttpResponseException.class)
  public void getByID_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(new DataNotFoundException()).when(stageService, "getByID", STAGE_OID);
    stageFacade.getByID(STAGE_ID);
  }


  @Test
  public void getAll_method_should_call_stageService_getAll_method() throws MemoryExcededException {
    stageFacade.getAll();
    verify(stageService, Mockito.times(1)).getAll();
  }

  @Test(expected = HttpResponseException.class)
  public void getAll_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(new MemoryExcededException("")).when(stageService, "getAll");
    stageFacade.getAll();
  }
}
