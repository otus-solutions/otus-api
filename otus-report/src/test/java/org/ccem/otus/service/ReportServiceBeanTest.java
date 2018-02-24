package org.ccem.otus.service;

import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.persistence.ReportDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.inject.Inject;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ReportServiceBeanTest {

    @Mock
    private ReportServiceBean reportServiceBean;

    private String requestParametersJson = "{\"recruitmentNumber\":322148795,\"reportId\":\"5a9199056ddc4f48a340b3ec\"}";

    private RequestParameters requestParameters;

    @Mock
    private ReportTemplate reportTemplate;

    private String template = "<span>teste</span>";

    @Mock
    private ReportDao reportDao;

    @Before
    public void setup(){
        requestParameters = RequestParameters.deserialize(requestParametersJson);
    }

    @Test
    public void method_get_data_sources() throws Exception{
        when(reportDao.findReport(requestParameters.getReportId())).thenReturn(reportTemplate);
        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        reportTemplate.getDataSources().add(participantDataSource);
        reportServiceBean.findReportById(requestParameters);

    }


}
