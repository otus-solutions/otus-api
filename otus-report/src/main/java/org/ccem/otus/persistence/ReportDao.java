package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.model.ReportTemplate;


public interface ReportDao {

   ReportTemplate findReport(ObjectId ri);

   boolean getResults(ReportTemplate reportTemplate);

}
