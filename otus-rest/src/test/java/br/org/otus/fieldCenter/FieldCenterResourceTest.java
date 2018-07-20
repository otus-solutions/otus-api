package br.org.otus.fieldCenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.fieldCenter.api.FieldCenterFacade;
import br.org.otus.rest.Response;

@RunWith(MockitoJUnitRunner.class)
public class FieldCenterResourceTest {
  private static final String FIELD_CENTER_ACRONYM = "SP";
  private static final String FIELD_CENTER_NAME = "Sao Paulo";
  private static final int FIELD_CENTER_CODE = 6;
  @InjectMocks
  private FieldCenterResource fieldCenterResource;
  @Mock
  private FieldCenterFacade fieldCenterFacade;
  private FieldCenter fieldCenter;
  private String jsonResponseExpected;
  private ArrayList<FieldCenter> fieldCenters;
  private String jsonResponseListExpected;

  @Before
  public void setUp() {
    fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(FIELD_CENTER_ACRONYM);
    fieldCenter.setName(FIELD_CENTER_NAME);
    fieldCenter.setCode(FIELD_CENTER_CODE);
    jsonResponseExpected = new Response().buildSuccess().toJson();
  }

  @Test
  public void createMethod_should_return_responseJson_and_verify_create_of_fieldCenterFacade() {
    assertEquals(jsonResponseExpected, fieldCenterResource.create(fieldCenter));
    verify(fieldCenterFacade, times(1)).create(fieldCenter);
  }

  @Test
  public void listMethod_should_return_json_of_responseList() {
    fieldCenters = new ArrayList<FieldCenter>();
    fieldCenters.add(fieldCenter);
    jsonResponseListExpected = new Response().setData(fieldCenters).toJson();
    when(fieldCenterFacade.list()).thenReturn(fieldCenters);
    assertEquals(jsonResponseListExpected, fieldCenterResource.list());
  }

  @Test
  public void updateMethod_should_return_jsonResponse_and_verify_update_of_fieldCenterFacade() {
    assertEquals(jsonResponseExpected, fieldCenterResource.update(fieldCenter));
    verify(fieldCenterFacade, times(1)).update(fieldCenter);
  }
}
