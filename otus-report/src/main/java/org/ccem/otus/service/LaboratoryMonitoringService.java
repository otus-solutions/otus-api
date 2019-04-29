package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ProgressReport;

import java.util.ArrayList;

public interface LaboratoryMonitoringService {

    ProgressReport getExamsProgress(ArrayList<String> possibleExams, ArrayList<Long> centerRns) throws DataNotFoundException;
}
