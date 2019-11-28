package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import java.util.LinkedList;

public interface ActivityProgressExtractionDao {
    LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) throws DataNotFoundException;
}
