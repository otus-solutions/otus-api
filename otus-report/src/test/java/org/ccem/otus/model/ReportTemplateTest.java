package org.ccem.otus.model;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ReportTemplateTest {
    private static final String LABEL = "labeTeste";
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2017,02,23,13,26);
    private static final String REPORT_TEMPLATE_JSON_NULL_TEMPLATE = "{\"label\":\"labeTeste\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private static final String REPORT_TEMPLATE_JSON_NULL_LABEL = "{\"template\":\"<span>teste</span>\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private static final String REPORT_TEMPLATE_JSON_NULL_SENDING_DATE = "{\"template\":\"<span>teste</span>\",\"label\":\"labeTeste\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private static final String REPORT_TEMPLATE_JSON_NULL_FIELD_CENTER = "{\"template\":\"<span>teste</span>\",\"label\":\"labeTeste\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private static final String REPORT_TEMPLATE_JSON_NULL_DATA_SOURCE = "{\"template\":\"<span>teste</span>\",\"label\":\"labeTeste\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"]}";
    private static final String REPORT_TEMPLATE_JSON_EMPTY_TEMPLATE = "{\"template\":\"  \",\"label\":\"labeTeste\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private static final String REPORT_TEMPLATE_JSON_EMPTY_LABEL = "{\"template\":\"<span>teste</span>\",\"label\":\"  \",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";
    private ReportTemplate reportTemplate;

    private String reportTemplateJson = "{\"template\":\"<span>teste</span>\",\"label\":\"labeTeste\",\"sendingDate\":\"2017-02-23T13:26:00Z\",\"fieldCenter\":[\"RS\",\"SP\"],\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";

    private String template = "<span>teste</span>";
    @Before
    public void setup(){

        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
        Whitebox.setInternalState(participantDataSource, "result", new ArrayList<>());

        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "fieldCenter", new ArrayList<>());
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        Whitebox.setInternalState(reportTemplate, "label", LABEL);
        Whitebox.setInternalState(reportTemplate, "sendingDate", LOCAL_DATE_TIME);

        reportTemplate.getDataSources().add(participantDataSource);
    }

    @Test
    public void method_get_data_sources_should_return_participantDataSource(){
        ArrayList<ReportDataSource> dataSources = reportTemplate.getDataSources();
        assertEquals("Participant",dataSources.get(0).getDataSource());
        assertTrue(dataSources.size()>0);
    }

    @Test
    public void method_serialize_should_return_json_of_reportTemplateJson(){
        reportTemplate.getFieldCenter().add("RS");
        reportTemplate.getFieldCenter().add("SP");
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_template_is_null() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_NULL_TEMPLATE);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_label_is_null() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_NULL_LABEL);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_sendingDate_is_null() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_NULL_SENDING_DATE);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_fieldCenter_is_null() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_NULL_FIELD_CENTER);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_dataSources_is_null() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_NULL_DATA_SOURCE);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_template_is_empty() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_EMPTY_TEMPLATE);
    }

    @Test(expected = ValidationException.class)
    public void method_validate_should_throw_ValidationException_when_label_is_empty() throws ValidationException {
        ReportTemplate.deserialize(REPORT_TEMPLATE_JSON_EMPTY_LABEL);
    }


    @Test
    public void method_deserialize_should_return_reportTemplate(){
        ReportTemplate report = null;
        try {
            report = ReportTemplate.deserialize(reportTemplateJson);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        reportTemplate.getFieldCenter().add("RS");
        reportTemplate.getFieldCenter().add("SP");
        assertEquals(template,report.getTemplate());
        assertTrue(reportTemplate.getDataSources().get(0) instanceof ParticipantDataSource);
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }
}
