package org.ccem.otus.importation.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;

import java.util.List;

public interface ImportService {

    List<ActivityImportResultDTO> importActivities(String acronym, Integer version, ActivityImportDTO surveyActivities) throws DataNotFoundException;

}
