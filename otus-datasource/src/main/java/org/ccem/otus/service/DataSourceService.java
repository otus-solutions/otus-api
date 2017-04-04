package org.ccem.otus.service;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.DataSource;

public interface DataSourceService {
	
	void create(DataSource dataSource) throws AlreadyExistException;

	List<DataSource> list();

	DataSource getByID(String id) throws DataNotFoundException;

}
