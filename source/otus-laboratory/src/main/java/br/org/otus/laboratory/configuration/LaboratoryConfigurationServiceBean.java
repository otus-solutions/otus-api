package br.org.otus.laboratory.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
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


  @Override
  public Boolean getCheckingExist() {
    return this.laboratoryConfigurationDao.getCheckingExist();
  }

  @Override
  public LaboratoryConfiguration getLaboratoryConfiguration() throws DataNotFoundException {
    return laboratoryConfigurationDao.find();
  }

  @Override
  public Set<TubeDefinition> getDefaultTubeSet() throws DataNotFoundException {
    return laboratoryConfigurationDao.find().getCollectGroupConfiguration().getDefaultCollectGroupDescriptor().getTubes();
  }

  @Override
  public Set<TubeDefinition> getTubeSetByGroupName(String groupName) throws DataNotFoundException {
    try {
      return laboratoryConfigurationDao.find().getCollectGroupConfiguration().getCollectGroupByName(groupName).getTubes();
    } catch (NoSuchElementException e) {
      return new HashSet<>();
    }
  }

  @Override
  public List<LabelReference> getLabelOrderByName(String orderName) throws DataNotFoundException {
    return laboratoryConfigurationDao.find().getLabelPrintConfiguration().getOrders().get(orderName);
  }

  @Override
  public List<String> generateCodes(TubeSeed seed) throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
    Integer startingPoint = this.laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount());
    laboratoryConfiguration.getCodeConfiguration().setLastInsertion(startingPoint + seed.getTubeCount());
    return laboratoryConfiguration.generateNewCodeList(seed, ++startingPoint);
  }

  @Override
  public AliquotConfiguration getAliquotConfiguration() throws DataNotFoundException {
    return laboratoryConfigurationDao.find().getAliquotConfiguration();
  }

  @Override
  public List<AliquoteDescriptor> getAliquotDescriptors() throws DataNotFoundException {
    return laboratoryConfigurationDao.find().getAliquotConfiguration().getAliquotDescriptors();
  }

  @Override
  public List<CenterAliquot> getAliquotDescriptorsByCenter(String center) throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
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
  public List<TubeCustomMetadata> getTubeCustomMedataData(String tubeType) throws DataNotFoundException {
    return laboratoryConfigurationDao.getTubeCustomMedataData(tubeType);
  }

  @Override
  public List<TubeCustomMetadata> getTubeCustomMedataData() {
    return laboratoryConfigurationDao.getTubeCustomMedataData();
  }

  @Override
  public List<LotReceiptCustomMetadata> getLotReceiptCustomMetadata() throws DataNotFoundException {
    return laboratoryConfigurationDao.getLotReceiptCustomMetadata();
  }

  @Override
  public Integer updateUnattachedLaboratoryLastInsertion() {
    return laboratoryConfigurationDao.updateUnattachedLaboratoryLastInsertion();
  }

}
