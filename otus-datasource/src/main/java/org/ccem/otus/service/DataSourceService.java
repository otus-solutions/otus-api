package org.ccem.otus.service;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;

public interface DataSourceService {
	
	void create(DataSource dataSource) throws AlreadyExistException;

	void update(DataSource dataSource) throws DataNotFoundException, ValidationException;

	List<DataSource> list();

	DataSource getByID(String id) throws DataNotFoundException;

	DataSourceElement getElementDataSource(String value) throws DataNotFoundException;

}
