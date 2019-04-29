package org.ccem.otus.service;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.persistence.ExamFlagReportDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;

@Stateless
public class LaboratoryMonitoringServiceBean implements LaboratoryMonitoringService {


    @Inject
    private ExamFlagReportDao examFlagReportDao;

    @Override
    public ProgressReport getExamsProgress(ArrayList<String> possibleExams, ArrayList<Long> centerRns) throws DataNotFoundException {
        LinkedList<String> linkedPossibleExams = new LinkedList<>(possibleExams);

        Document flagReportDocument = examFlagReportDao.getExamProgressReport(linkedPossibleExams, centerRns);

        return getProgressReport(linkedPossibleExams, flagReportDocument);
    }

    private ProgressReport getProgressReport(LinkedList<String> headers, Document flagReportDocument) {
        ProgressReport progressReport = ProgressReport.deserialize(flagReportDocument.toJson());
        progressReport.setColumns(headers);
        return progressReport;
    }
}
