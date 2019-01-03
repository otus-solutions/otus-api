package org.ccem.otus.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;
import org.ccem.otus.persistence.DataSourceDao;

@Stateless
public class DataSourceServiceBean implements DataSourceService {
	
	@Inject
	private DataSourceDao dataSourceDao;

	@Override
	public void create(DataSource dataSource) throws AlreadyExistException {
		dataSourceDao.persist(dataSource);
	}

	@Override
	public void update(DataSource dataSource) throws ValidationException, DataNotFoundException {
		DataSource dataSourcePersisted = dataSourceDao.findByID(dataSource.getId());
		if(dataSource.getDataAsSet().containsAll(dataSourcePersisted.getDataAsSet())){
			if (dataSource.getDataAsSet().size() > dataSourcePersisted.getDataAsSet().size()){
				dataSourceDao.update(dataSource);
			} else {
				throw new ValidationException(new Throwable("There are same elements in datasource {" + dataSource.getId() + "}"));
			}
		} else {
			throw new ValidationException(new Throwable("There are missing elements in datasource {" + dataSource.getId() + "}"));
		}
	}



	@Override
	public List<DataSource> list() {
		return dataSourceDao.find();
	}

	@Override
	public DataSource getByID(String id) throws DataNotFoundException {
		return dataSourceDao.findByID(id);
	}

}
