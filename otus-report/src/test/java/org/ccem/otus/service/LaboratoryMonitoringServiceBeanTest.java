package org.ccem.otus.service;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.persistence.ExamFlagReportDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProgressReport.class})
public class LaboratoryMonitoringServiceBeanTest {
    private static LinkedList<String> POSSIBLE_EXAMS = new LinkedList<>();
    private static ArrayList<Long> CENTER_RNS = new ArrayList<>();
    private static Document PROGRESS_REPORT_DOCUMENT = new Document();

    @InjectMocks
    private LaboratoryMonitoringServiceBean laboratoryMonitoringService;

    @Mock
    private ExamFlagReportDao examFlagReportDao;

    @Mock
    private ProgressReport progressReport;

    @Before
    public void setUp() throws Exception {
        mockStatic(ProgressReport.class);
        PowerMockito.when(ProgressReport.class, "deserialize", PROGRESS_REPORT_DOCUMENT.toJson()).thenReturn(progressReport);
    }

//    @Test
//    public void getExamFlagReport_should_call_the_DAO_and_set_columns_on_the_progress_report() throws Exception {
//        LinkedList<String> possibleExams = new LinkedList<>();
//        ArrayList<Long> centerRns = new ArrayList<>();
//        Mockito.when(examFlagReportDao.getExamProgressReport(possibleExams, centerRns)).thenReturn(PROGRESS_REPORT_DOCUMENT);
//
//        laboratoryMonitoringService.getExamFlagReport(possibleExams, centerRns);
//        Mockito.verify(examFlagReportDao).getExamProgressReport(possibleExams,centerRns);
//        Mockito.verify(progressReport).setColumns(possibleExams);
//    }
}