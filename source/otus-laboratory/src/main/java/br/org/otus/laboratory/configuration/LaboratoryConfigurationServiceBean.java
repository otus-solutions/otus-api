package br.org.otus.laboratory.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotCenterDescriptors;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.aliquot.CenterAliquot;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.label.LabelReference;

@Stateless
public class LaboratoryConfigurationServiceBean implements LaboratoryConfigurationService {

  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  private LaboratoryConfiguration laboratoryConfiguration;

  @PostConstruct
  public void loadLaboratoryConfiguration() {
    this.laboratoryConfiguration = this.laboratoryConfigurationDao.find();
  }

  @Override
  public Boolean getCheckingExist() {
    return this.laboratoryConfigurationDao.getCheckingExist();
  }

  @Override
  public Set<TubeDefinition> getDefaultTubeSet() {
    return this.laboratoryConfiguration.getCollectGroupConfiguration().getDefaultCollectGroupDescriptor().getTubes();
  }

  @Override
  public Set<TubeDefinition> getTubeSetByGroupName(String groupName) {
    try {
      return this.laboratoryConfiguration.getCollectGroupConfiguration().getCollectGroupByName(groupName).getTubes();
    } catch (NoSuchElementException e) {
      return new HashSet<>();
    }
  }

  @Override
  public List<LabelReference> getLabelOrderByName(String orderName) {
    return laboratoryConfiguration.getLabelPrintConfiguration().getOrders().get(orderName);
  }

  @Override
  public List<String> generateCodes(TubeSeed seed) {
    Integer startingPoint = this.laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount());
    this.laboratoryConfiguration.getCodeConfiguration().setLastInsertion(startingPoint + seed.getTubeCount());
    return this.laboratoryConfiguration.generateNewCodeList(seed, ++startingPoint);
  }

  public LaboratoryConfiguration getLaboratoryConfiguration() {
    return laboratoryConfiguration;
  }

  @Override
  public AliquotConfiguration getAliquotConfiguration() {
    return laboratoryConfiguration.getAliquotConfiguration();
  }

  @Override
  public List<AliquoteDescriptor> getAliquotDescriptors() {
    return laboratoryConfiguration.getAliquotConfiguration().getAliquotDescriptors();
  }

  @Override
  public List<CenterAliquot> getAliquotDescriptorsByCenter(String center) throws DataNotFoundException {
    AliquotCenterDescriptors first = laboratoryConfiguration.getAliquotConfiguration().getAliquotCenterDescriptors().stream()
      .filter(aliquotCenterDescriptor -> aliquotCenterDescriptor.getName().equals(center)).findFirst().orElseThrow(() -> new DataNotFoundException("FieldCenter not found"));
    return first.getAllCenterAliquots();
  }

  @Override
  public AliquoteDescriptor getAliquotDescriptorsByName(String name) throws DataNotFoundException {
    AliquoteDescriptor aliquotByName = getAliquotDescriptors().stream().filter(aliquoteDescriptor -> aliquoteDescriptor.getName().equals(name)).findFirst()
      .orElseThrow(() -> new DataNotFoundException("Any descriptor found for \"" + name + "\""));
    return aliquotByName;
  }

  @Override
  public AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException {
    return laboratoryConfigurationDao.getAliquotExamCorrelation();
  }

  @Override
  public List<String> listPossibleExams(String center) throws DataNotFoundException {
    ArrayList centerAliquots = laboratoryConfigurationDao.listCenterAliquots(center);
    return laboratoryConfigurationDao.getAliquotsExams(centerAliquots);
  }

  @Override
  public List<TubeCustomMetadata> getTubeCustomMedataDataByType(String type) throws DataNotFoundException {
    return laboratoryConfigurationDao.getTubeCustomMedataDataByType(type);
  }
}
