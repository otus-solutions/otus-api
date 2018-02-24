package org.ccem.otus.model;

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

    private String reportTemplateJson = "{\"template\":\"\\u003cspan\\u003eteste\\u003c/span\\u003e\",\"dataSources\":[{\"result\":[],\"dataSource\":\"Participant\"}]}";

    private String template = "<span>teste</span>";
    @Before
    public void setup(){
        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");

        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        reportTemplate.getDataSources().add(participantDataSource);
    }

    @Test
    public void method_get_data_sources(){
        ArrayList<ReportDataSource> dataSources = reportTemplate.getDataSources();
        assertTrue(dataSources.size()>0);
    }

    @Test
    public void method_serialize(){
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }

    @Test
    public void method_deserialize(){
        ReportTemplate report = ReportTemplate.deserialize(reportTemplateJson);
        assertEquals(template,report.getTemplate());
        assertTrue(reportTemplate.getDataSources().get(0) instanceof ParticipantDataSource);
        assertEquals(reportTemplateJson,ReportTemplate.serialize(reportTemplate));
    }
}
