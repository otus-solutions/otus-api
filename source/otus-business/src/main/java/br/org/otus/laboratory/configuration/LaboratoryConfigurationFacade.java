package br.org.otus.laboratory.configuration;


import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.List;

public class LaboratoryConfigurationFacade {

  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;


  public List<TubeCustomMetadata> getTubeMedataDataByType(String tubeType) {
    try{
      return laboratoryConfigurationService.getTubeCustomMedataDataByType(tubeType);
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
