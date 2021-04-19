package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.List;

public class LaboratoryConfigurationFacade {

  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;

  public Boolean getCheckingExist() {
    return laboratoryConfigurationService.getCheckingExist();
  }

  public LaboratoryConfigurationDTO getLaboratoryConfiguration() {
    try{
      LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationService.getLaboratoryConfiguration();
      return new LaboratoryConfigurationDTO(laboratoryConfiguration);
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public AliquotConfiguration getAliquotConfiguration() {
    try{
      return laboratoryConfigurationService.getAliquotConfiguration();
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<AliquoteDescriptor> getAliquotDescriptors() {
    try{
      return laboratoryConfigurationService.getAliquotDescriptors();
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<TubeCustomMetadata> getTubeMedataData(String tubeType) {
    try{
      return laboratoryConfigurationService.getTubeCustomMedataData(tubeType);
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<LotReceiptCustomMetadata> getLotReceiptCustomMetadata() {
    try{
      return laboratoryConfigurationService.getLotReceiptCustomMetadata();
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadataOptions(String materialType) {
    try{
      return laboratoryConfigurationService.getMaterialReceiptCustomMetadataOptions(materialType);
    }catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadataOptions() {
    return laboratoryConfigurationService.getMaterialReceiptCustomMetadataOptions();
  }
}
