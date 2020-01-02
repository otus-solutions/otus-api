package org.ccem.otus.persistence;

import java.util.LinkedList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

public interface ActivityProgressExtractionDao {

  LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) throws DataNotFoundException;

}
