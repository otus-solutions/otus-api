package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;



public interface ReportDao {

   ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException;
   
   ObjectId insert(ReportTemplate report);

   boolean getResults(ReportTemplate reportTemplate);
   
   void deleteById(String id) throws DataNotFoundException;
   
   List<ReportTemplate> getAll();
   
   List<ReportTemplate> getByCenter(String fieldCenter);
   
   ReportTemplate getById(String id) throws DataNotFoundException;
   
   ReportTemplate update(ReportTemplate examsLot) throws DataNotFoundException;

}
