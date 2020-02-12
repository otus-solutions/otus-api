package br.org.otus.laboratory;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import unattached.UnattachedLaboratory;
import unattached.UnattachedLaboratoryService;

import javax.inject.Inject;

public class UnattachedLaboratoryFacade {

  @Inject
  private UnattachedLaboratoryService unattachedLaboratoryService;
  @Inject
  private FieldCenterDao fieldCenterDao;
  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  public UnattachedLaboratory create(String fieldCenterAcronym, String collectGroupDescriptorName) {
    FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(fieldCenterAcronym);
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
    Integer lastInsertion = laboratoryConfigurationDao.updateUnattachedLaboratoryLastInsertion();
    CollectGroupDescriptor collectGroupDescriptor = laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(collectGroupDescriptorName);
    return unattachedLaboratoryService.create(lastInsertion, collectGroupDescriptor, fieldCenter);
  }
}
