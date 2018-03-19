package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;


public interface ReportDao {

   ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException;
   
   ObjectId insert(ReportTemplate report);

   boolean getResults(ReportTemplate reportTemplate);

}
