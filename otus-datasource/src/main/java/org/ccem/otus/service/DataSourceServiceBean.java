package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;
import org.ccem.otus.persistence.DataSourceDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DataSourceServiceBean implements DataSourceService {
	
	@Inject
	private DataSourceDao dataSourceDao;

	public DataSourceServiceBean (){};

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

	@Override
	public DataSourceElement getElementDatasource(String value) throws DataNotFoundException {
		return dataSourceDao.getElementDatasource(value);
	}

}
