package org.ccem.otus.service;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;
import org.ccem.otus.model.dataSources.*;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;

import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReportServiceBean.class)
public class ReportServiceBeanTest {

    @InjectMocks
    private ReportServiceBean reportServiceBean;

    private String requestParametersJson = "{\"recruitmentNumber\":322148795,\"reportId\":\"5a9199056ddc4f48a340b3ec\"}";

    private RequestParameters requestParameters;

    @Mock
    private ReportTemplate reportTemplate;

    private String template = "<span>teste</span>";

    @Mock
    private ReportDao reportDao;

    @Mock
    private ParticipantDataSourceDao participantDataSourceDao;

    @Mock
    private ActivityDataSourceDao activityDataSourceDao;

    @Before
    public void setup(){
        requestParameters = RequestParameters.deserialize(requestParametersJson);
    }

    @Test
    public void method_find_report_by_id_instanceof_ParticipantDataSource() throws Exception{
        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        reportTemplate.getDataSources().add(participantDataSource);
        when(reportDao.findReport(requestParameters.getReportId())).thenReturn(reportTemplate);

        FieldCenter fieldCenterInstance = new FieldCenter();
        Whitebox.setInternalState(fieldCenterInstance, "acronym", "RS");

        ImmutableDate immutableDateInstance = new ImmutableDate("2018-02-22 00:00:00.000");

        ParticipantDataSourceResult participantDataSourceResult = new ParticipantDataSourceResult();
        Whitebox.setInternalState(participantDataSourceResult, "name", "Joao");
        Whitebox.setInternalState(participantDataSourceResult, "sex", "masc");
        Whitebox.setInternalState(participantDataSourceResult, "recruitmentNumber", (long) 123456789);
        Whitebox.setInternalState(participantDataSourceResult, "fieldCenter", fieldCenterInstance);
        Whitebox.setInternalState(participantDataSourceResult, "birthdate", immutableDateInstance);
        when(participantDataSourceDao.getResult((long)322148795, participantDataSource)).thenReturn(participantDataSourceResult);
        ReportTemplate response = reportServiceBean.findReportById(requestParameters);

        assertTrue(response.getDataSources().get(0).getResult().get(0) instanceof ParticipantDataSourceResult);
        assertEquals(participantDataSourceResult.toString(),response.getDataSources().get(0).getResult().get(0).toString());
    }

    @Test
    public void method_find_report_by_id_instanceof_activity_data_source() throws Exception{
        ActivityDataSource activityDataSource = new ActivityDataSource();
        Whitebox.setInternalState(activityDataSource, "dataSource", "Activity");
        reportTemplate = new ReportTemplate();
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        reportTemplate.getDataSources().add(activityDataSource);
        when(reportDao.findReport(requestParameters.getReportId())).thenReturn(reportTemplate);

        ActivityStatusOptions activityStatusOptions = ActivityStatusOptions.CREATED;

        User user = new User();
        Whitebox.setInternalState(user, "name", "Joao");

        ActivityStatus activityStatus = new ActivityStatus();
        Whitebox.setInternalState(activityStatus, "objectType", "ActivityStatus");
        Whitebox.setInternalState(activityStatus, "name", activityStatusOptions);
        Whitebox.setInternalState(activityStatus, "date", LocalDateTime.now());
        Whitebox.setInternalState(activityStatus, "user", user);

        ArrayList<ActivityStatus> statusHistory = new ArrayList<>();
        statusHistory.add(activityStatus);
        ActivityDataSourceResult activityDataSourceResult = new ActivityDataSourceResult();


        when(activityDataSourceDao.getResult((long)322148795, activityDataSource)).thenReturn(activityDataSourceResult);
        ReportTemplate response = reportServiceBean.findReportById(requestParameters);

        assertTrue(response.getDataSources().get(0).getResult().get(0) instanceof ActivityDataSourceResult);
        assertEquals(activityDataSourceResult.toString(),response.getDataSources().get(0).getResult().get(0).toString());
    }


}
