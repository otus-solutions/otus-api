package org.ccem.otus.service;

import java.util.List;

import com.google.gson.JsonArray;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;

public interface DataSourceService {
	
	void create(DataSource dataSource, JsonArray duplicatedElements) throws AlreadyExistException, ValidationException;

	void update(DataSource dataSource, JsonArray duplicatedElements) throws DataNotFoundException, ValidationException;

	List<DataSource> list();

	DataSource getByID(String id) throws DataNotFoundException;

	DataSourceElement getElementDataSource(String value) throws DataNotFoundException;

}
