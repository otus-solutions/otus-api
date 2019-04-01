package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.DataSourceElement;
import org.ccem.otus.persistence.DataSourceDao;
import org.ccem.otus.utils.DataSourceValuesMapping;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Stateless
public class DataSourceServiceBean implements DataSourceService {

  private DataSourceValuesMapping dataSourceValuesMapping;

  @Inject
	private DataSourceDao dataSourceDao;


	public DataSourceServiceBean (){};

	@Override
	public void create(DataSource dataSource, HashSet<String> duplicatedElements) throws AlreadyExistException, ValidationException {
		if (duplicatedElements.size() > 0){
			throw new ValidationException(new Throwable("There are duplicated elements in datasource {" + duplicatedElements + "}"),duplicatedElements);
		}else {
			dataSourceDao.persist(dataSource);
		}
	}

	@Override
	public void update(DataSource dataSource, HashSet<String> duplicatedElements) throws ValidationException, DataNotFoundException {
		DataSource dataSourcePersisted = dataSourceDao.findByID(dataSource.getId());

		if(dataSource.getDataAsSet().containsAll(dataSourcePersisted.getDataAsSet())){
			if (duplicatedElements.size() == 0){
				dataSourceDao.update(dataSource);
			} else {
				throw new ValidationException(new Throwable("There are duplicated elements in datasource {" + duplicatedElements + "}"));
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
	public DataSourceElement getElementDataSource(String value) {
		return dataSourceDao.getElementDataSource(value);
	}

	@Override
	public String getElementExtractionValue(List<String> dataSources, String value) {
		String valueFound = "";
		for(String dataSource : dataSources){
			String extractionValue = this.dataSourceValuesMapping.getExtractionValue(dataSource, value);
			if(extractionValue != null){
				valueFound = extractionValue;
			}
		}
		if(valueFound.equals("")){
			valueFound = "NOT IDENTIFIED";
		}
		return valueFound;
	}

	@Override
    public void populateDataSourceMapping() {
	  this.dataSourceValuesMapping = dataSourceDao.getDataSourceMapping();
    }

}

