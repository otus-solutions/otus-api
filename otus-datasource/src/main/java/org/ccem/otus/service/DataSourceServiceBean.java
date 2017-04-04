package org.ccem.otus.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.DataSource;
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
	public List<DataSource> list() {
		return dataSourceDao.find();
	}

	@Override
	public DataSource getByID(String id) throws DataNotFoundException {
		return dataSourceDao.findByID(id);
	}

}
