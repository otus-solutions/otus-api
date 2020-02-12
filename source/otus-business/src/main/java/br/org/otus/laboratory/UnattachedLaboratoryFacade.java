package br.org.otus.laboratory;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import br.org.otus.laboratory.unattached.service.UnattachedLaboratoryService;

import javax.inject.Inject;

public class UnattachedLaboratoryFacade {

  @Inject
  private UnattachedLaboratoryService unattachedLaboratoryService;
  @Inject
  private FieldCenterDao fieldCenterDao;
  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  public void create(String userEmail, String fieldCenterAcronym, String collectGroupDescriptorName) {
    FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(fieldCenterAcronym);
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
    if (laboratoryConfiguration != null){
      Integer lastInsertion = laboratoryConfigurationDao.updateUnattachedLaboratoryLastInsertion();
      CollectGroupDescriptor collectGroupDescriptor;
      try {
        collectGroupDescriptor = laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(collectGroupDescriptorName);
      } catch (Exception e) {
        throw new HttpResponseException(Validation.build("Collect group not found"));
      }
      unattachedLaboratoryService.create(userEmail, lastInsertion, collectGroupDescriptor, fieldCenter);
    } else {
      throw new HttpResponseException(Validation.build("Laboratory configuration not found"));
    }
  }

  public ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) {
    try {
      return unattachedLaboratoryService.find(fieldCenterAcronym, collectGroupDescriptorName, page, quantityByPage);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
}
