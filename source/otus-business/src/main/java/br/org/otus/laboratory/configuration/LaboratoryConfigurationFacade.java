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


  public List<TubeCustomMetadata> getTubeMedataData(String tubeType) {
    try{
      return laboratoryConfigurationService.getTubeCustomMedataData(tubeType);
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }
}
