package br.org.otus.datasource.api;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.DataSource;
import org.ccem.otus.service.DataSourceService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class DataSourceFacade {

  @Inject
  private DataSourceService dataSourceService;

  public void create(DataSource dataSource, HashSet<String> duplicatedElements) {
    try {
      dataSourceService.create(dataSource, duplicatedElements);
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (AlreadyExistException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void update(DataSource dataSource, HashSet<String> duplicatedElements) {
    try {
      dataSourceService.update(dataSource, duplicatedElements);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<DataSource> getAll() {
    return dataSourceService.list();
  }

  public DataSource getByID(String id) {
    try {
      return dataSourceService.getByID(id);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
