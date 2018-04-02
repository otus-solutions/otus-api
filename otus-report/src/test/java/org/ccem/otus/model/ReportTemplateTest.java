package org.ccem.otus.model;

import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ReportTemplateTest {

    private ReportTemplate reportTemplate;

    private String reportTemplateJson = "{\"template\":\"<span>teste</span>\",\"dataSources\":[{\"dataSource\":\"Participant\",\"result\":[]}]}";

    private String template = "<span>teste</span>";
    @Before
    public void setup(){
        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
        Whitebox.setInternalState(participantDataSource, "result", new ArrayList<>());

        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
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
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }

    @Test
    public void method_deserialize_should_return_reportTemplate(){
        ReportTemplate report = ReportTemplate.deserialize(reportTemplateJson);
        assertEquals(template,report.getTemplate());
        assertTrue(reportTemplate.getDataSources().get(0) instanceof ParticipantDataSource);
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }
}
