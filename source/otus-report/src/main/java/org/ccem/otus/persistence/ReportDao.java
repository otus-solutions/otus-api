package org.ccem.otus.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;

public interface ReportDao {

  ActivityReportTemplate insertActivityReport(ActivityReportTemplate activityReportTemplate) throws ValidationException;

  ActivityReportTemplate getActivityReport(String acronym, Integer version) throws DataNotFoundException;

  List<ActivityReportTemplate> getActivityReportList(String acronym) throws DataNotFoundException;

  void updateActivityReport(ObjectId reportId, ArrayList<Integer> versions) throws DataNotFoundException;

  ReportTemplate insert(ReportTemplate report);

  ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException, ValidationException;

  void deleteById(String id) throws DataNotFoundException;

  List<ReportTemplate> getAll() throws ValidationException;

  List<ReportTemplateDTO> getByCenter(String fieldCenter) throws ValidationException;

  ReportTemplate getById(String id) throws DataNotFoundException, ValidationException;

  ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException;
}
