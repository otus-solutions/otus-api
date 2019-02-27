package org.ccem.otus.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;
import org.ccem.otus.persistence.DataSourceDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class DataSourceServiceBeanTest {

  private static final String VALUE_1 = "01 MEDICAMENTO";
  private static final String VALUE_2 = "02 CURATIVO";
  private static final String EXTRACTION_VALUE_1 = "1";
  private static final String EXTRACTION_VALUE_2 = "2";
  private static final String VALUE_FIELD = "value";
  private static final String EXTRACTION_FIELD = "extractionValue";
  private static final String ID = "test";
  private static final String NAME = "TESTE";

  @InjectMocks
  private DataSourceServiceBean dataSourceServiceBean;

  @Mock
  private DataSourceDao dataSourceDao;
  @Mock
  private HashSet<String> duplicatedElements = new HashSet<>();

  private DataSource DATASOURCE;
  private DataSource DATASOURCE_PERSISTED;
  private JsonArray elements = new JsonArray();
  private JsonArray elements_persisted = new JsonArray();

  @Before
  public void setUp(){
    JsonObject object = new JsonObject();
    object.addProperty(VALUE_FIELD, VALUE_1);
    object.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_1);
    elements_persisted.add(object);
    DATASOURCE_PERSISTED = new DataSource(ID,NAME,elements_persisted);
  }

  @Test
  public void should_method_update_datasource_with_success() throws DataNotFoundException, ValidationException {
    JsonObject object1 = new JsonObject();
    object1.addProperty(VALUE_FIELD, VALUE_1);
    object1.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_1);
    JsonObject object2 = new JsonObject();
    object2.addProperty(VALUE_FIELD, VALUE_2);
    object2.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_2);
    elements.add(object1);
    elements.add(object2);
    DATASOURCE = new DataSource(ID,NAME,elements);
    when(dataSourceDao.findByID(DATASOURCE.getId())).thenReturn(DATASOURCE_PERSISTED);
    dataSourceServiceBean.update(DATASOURCE,duplicatedElements);
    Mockito.verify(dataSourceDao, times(1)).findByID(ID);
    Mockito.verify(dataSourceDao, times(1)).update(DATASOURCE);
  }

  @Test(expected = ValidationException.class)
  public void should_method_update_datasource_with_exception_missing_elements() throws DataNotFoundException, ValidationException {
    JsonObject object1 = new JsonObject();
    object1.addProperty(VALUE_FIELD, VALUE_2);
    object1.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_2);
    elements.add(object1);
    DATASOURCE = new DataSource(ID,NAME,elements);
    when(dataSourceDao.findByID(DATASOURCE.getId())).thenReturn(DATASOURCE_PERSISTED);
    dataSourceServiceBean.update(DATASOURCE,duplicatedElements);
    Mockito.doThrow(new ValidationException()).when(dataSourceServiceBean).update(DATASOURCE,duplicatedElements);
  }

  @Test
  public void should_method_getElementDataSource_return_a_DataSourceElement() throws DataNotFoundException {
    DataSourceElement dataSourceElement = new DataSourceElement(VALUE_1,EXTRACTION_VALUE_1);
    when(dataSourceDao.getElementDataSource(VALUE_1)).thenReturn(dataSourceElement);
    Assert.assertEquals(dataSourceServiceBean.getElementDataSource(VALUE_1), dataSourceElement);
    verify(dataSourceDao, times(1)).getElementDataSource(VALUE_1);
  }

  @Test(expected = DataNotFoundException.class)
  public void should_method_getElementDataSource_return_a_DataNotFoundException() throws DataNotFoundException {
    when(dataSourceDao.getElementDataSource(VALUE_1)).thenThrow(DataNotFoundException.class);
    dataSourceServiceBean.getElementDataSource(VALUE_1);
    verify(dataSourceDao, times(1)).getElementDataSource(VALUE_1);
    Mockito.doThrow(new DataNotFoundException()).when(dataSourceServiceBean).getElementDataSource(VALUE_1);
  }
}