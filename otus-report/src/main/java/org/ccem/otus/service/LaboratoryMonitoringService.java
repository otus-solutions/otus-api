package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ProgressReport;

import java.util.ArrayList;
import java.util.LinkedList;

public interface LaboratoryMonitoringService {

    ProgressReport getExamFlagReport(LinkedList<String> possibleExams, ArrayList<Long> centerRns) throws DataNotFoundException;
}
