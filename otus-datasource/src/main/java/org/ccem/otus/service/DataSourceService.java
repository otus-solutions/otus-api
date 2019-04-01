package org.ccem.otus.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;

public interface DataSourceService {
	
	void create(DataSource dataSource, HashSet<String> duplicatedElements) throws AlreadyExistException, ValidationException;

	void update(DataSource dataSource, HashSet<String> duplicatedElements) throws DataNotFoundException, ValidationException;

	List<DataSource> list();

	DataSource getByID(String id) throws DataNotFoundException;

	DataSourceElement getElementDataSource(String value) throws DataNotFoundException;

	String getElementExtractionValue(List<String> dataSources, String value);

	void populateDataSourceMapping();
}
