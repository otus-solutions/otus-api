package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;


public interface DataSourceDao {
	
	void persist(DataSource dataSource) throws AlreadyExistException;

	void update(DataSource dataSource) throws ValidationException, DataNotFoundException;

	List<DataSource> find();

	DataSource findByID(String id) throws DataNotFoundException;

	public DataSourceElement getElementDatasource(String value) throws DataNotFoundException;

}
