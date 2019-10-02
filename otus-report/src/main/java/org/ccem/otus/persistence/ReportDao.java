package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;

public interface ReportDao {

	ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException, ValidationException;

	ReportTemplate getActivityReport(String acronym, Integer version) throws DataNotFoundException, ValidationException;

	ReportTemplate insert(ReportTemplate report);

	void deleteById(String id) throws DataNotFoundException;

	List<ReportTemplate> getAll() throws ValidationException;

	List<ReportTemplateDTO> getByCenter(String fieldCenter) throws ValidationException;

	ReportTemplate getById(String id) throws DataNotFoundException, ValidationException;

	ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException;

}
