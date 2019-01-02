package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.DataSource;


public interface DataSourceDao {
	
	void persist(DataSource dataSource) throws AlreadyExistException;

	void update(DataSource dataSource) throws AlreadyExistException, DataNotFoundException;

	List<DataSource> find();

	DataSource findByID(String id) throws DataNotFoundException;

}
