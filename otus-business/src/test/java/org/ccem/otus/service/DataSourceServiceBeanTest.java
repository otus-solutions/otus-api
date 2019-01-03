package org.ccem.otus.service;

import br.org.otus.response.info.Validation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.persistence.DataSourceDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
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
        dataSourceServiceBean.update(DATASOURCE);
        Mockito.verify(dataSourceDao, times(1)).findByID(ID);
        Mockito.verify(dataSourceDao, times(1)).update(DATASOURCE);
    }

    @Test(expected = ValidationException.class)
    public void should_method_update_datasource_with_exception_same_elements() throws DataNotFoundException, ValidationException {
        JsonObject object1 = new JsonObject();
        object1.addProperty(VALUE_FIELD, VALUE_1);
        object1.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_1);
        elements.add(object1);
        DATASOURCE = new DataSource(ID,NAME,elements);
        when(dataSourceDao.findByID(DATASOURCE.getId())).thenReturn(DATASOURCE_PERSISTED);
        dataSourceServiceBean.update(DATASOURCE);
        Mockito.doThrow(new ValidationException()).when(dataSourceServiceBean).update(DATASOURCE);
    }

    @Test(expected = ValidationException.class)
    public void should_method_update_datasource_with_exception_missing_elements() throws DataNotFoundException, ValidationException {
        JsonObject object1 = new JsonObject();
        object1.addProperty(VALUE_FIELD, VALUE_2);
        object1.addProperty(EXTRACTION_FIELD, EXTRACTION_VALUE_2);
        elements.add(object1);
        DATASOURCE = new DataSource(ID,NAME,elements);
        when(dataSourceDao.findByID(DATASOURCE.getId())).thenReturn(DATASOURCE_PERSISTED);
        dataSourceServiceBean.update(DATASOURCE);
        Mockito.doThrow(new ValidationException()).when(dataSourceServiceBean).update(DATASOURCE);
    }
}