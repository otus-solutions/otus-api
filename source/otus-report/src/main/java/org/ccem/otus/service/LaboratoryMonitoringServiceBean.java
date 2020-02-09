package org.ccem.otus.service;

import br.org.otus.laboratory.project.exam.examInapplicability.persistence.ExamInapplicabilityDao;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.persistence.ExamFlagReportDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class LaboratoryMonitoringServiceBean implements LaboratoryMonitoringService {

  @Inject
  private ExamFlagReportDao examFlagReportDao;
  @Inject
  private ExamInapplicabilityDao examInapplicabilityDao;

  @Override
  public ProgressReport getExamFlagReport(LinkedList<String> possibleExams, ArrayList<Long> centerRns) throws DataNotFoundException {
    List<Document> examInapplicabilities = new ArrayList();
    MongoCursor<Document> inapplicabilitiesCursor = examInapplicabilityDao.list().iterator();
    while (inapplicabilitiesCursor.hasNext()) {
      examInapplicabilities.add(inapplicabilitiesCursor.next());
    }
    Document flagReportDocument = examFlagReportDao.getExamProgressReport(possibleExams, centerRns, examInapplicabilities);
    return getProgressReport(possibleExams, flagReportDocument);
  }

  private ProgressReport getProgressReport(LinkedList<String> headers, Document flagReportDocument) {
    ProgressReport progressReport = ProgressReport.deserialize(flagReportDocument.toJson());
    progressReport.setColumns(headers);
    return progressReport;
  }
}
