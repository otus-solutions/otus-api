package br.org.otus.importation.activity;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.importation.activity.ActivityImportResultDTO;
import org.ccem.otus.importation.service.ImportService;

import javax.inject.Inject;
import java.util.List;

public class ActivityImportationFacade {

  @Inject
  private ImportService importService;

  public List<ActivityImportResultDTO> importActivities(String acronym, Integer version, ActivityImportDTO surveyActivities) {
    try {
      return importService.importActivities(acronym, version, surveyActivities);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
