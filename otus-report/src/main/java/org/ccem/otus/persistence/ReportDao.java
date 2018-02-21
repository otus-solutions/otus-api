package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;


public interface ReportDao {

   ReportTemplate findReport(ObjectId ri) throws DataNotFoundException;

   boolean getResults(ReportTemplate reportTemplate);

}
