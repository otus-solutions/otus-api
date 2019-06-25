package org.ccem.otus.service;

import br.org.otus.laboratory.project.exam.examInapplicability.persistence.ExamInapplicabilityDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.persistence.ExamFlagReportDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProgressReport.class})
public class LaboratoryMonitoringServiceBeanTest {
    private static LinkedList<String> POSSIBLE_EXAMS = new LinkedList<>();
    private static ArrayList<Long> CENTER_RNS = new ArrayList<>();
    private static Document PROGRESS_REPORT_DOCUMENT = new Document();
    private static List<Document> EXAM_INAPPLICABILITIES = new ArrayList();
    private static Document EXAM_INAPPLICABILITY_RESULT =  ParseQuery.toDocument("");

    @InjectMocks
    private LaboratoryMonitoringServiceBean laboratoryMonitoringService;

    @Mock
    private ExamFlagReportDao examFlagReportDao;

    @Mock
    private ProgressReport progressReport;

    @Mock
    private ExamInapplicabilityDao examInapplicabilityDao;

    @Mock
    private FindIterable<Document> result;

    @Mock
    private MongoCursor<Document> inapplicabilitiesCursor;

    @Before
    public void setUp() throws Exception {
        mockStatic(ProgressReport.class);
        PowerMockito.when(ProgressReport.class, "deserialize", PROGRESS_REPORT_DOCUMENT.toJson()).thenReturn(progressReport);
    }

    @Test
    public void getExamFlagReport_should_call_the_DAO_and_set_columns_on_the_progress_report() throws Exception {
        when(examInapplicabilityDao.list()).thenReturn(result);
        when(result.iterator()).thenReturn(inapplicabilitiesCursor);
        when(examFlagReportDao.getExamProgressReport(POSSIBLE_EXAMS, CENTER_RNS,EXAM_INAPPLICABILITIES)).thenReturn(PROGRESS_REPORT_DOCUMENT);

        laboratoryMonitoringService.getExamFlagReport(POSSIBLE_EXAMS, CENTER_RNS);
        verify(examFlagReportDao).getExamProgressReport(POSSIBLE_EXAMS, CENTER_RNS,EXAM_INAPPLICABILITIES);
        verify(progressReport).setColumns(POSSIBLE_EXAMS);
    }
}