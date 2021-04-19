package br.org.otus.laboratory.configuration;

import java.util.List;
import java.util.Set;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.aliquot.CenterAliquot;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.label.LabelReference;

public interface LaboratoryConfigurationService {

  Boolean getCheckingExist();

  Set<TubeDefinition> getDefaultTubeSet() throws DataNotFoundException;

  Set<TubeDefinition> getTubeSetByGroupName(String setName) throws DataNotFoundException;

  List<LabelReference> getLabelOrderByName(String orderName) throws DataNotFoundException;

  List<String> generateCodes(TubeSeed seed) throws DataNotFoundException;

  LaboratoryConfiguration getLaboratoryConfiguration() throws DataNotFoundException;

  AliquotConfiguration getAliquotConfiguration() throws DataNotFoundException;

  List<AliquoteDescriptor> getAliquotDescriptors() throws DataNotFoundException;

  List<CenterAliquot> getAliquotDescriptorsByCenter(String center) throws DataNotFoundException;

  AliquoteDescriptor getAliquotDescriptorsByName(String name) throws DataNotFoundException;

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  List<String> listPossibleExams(String center) throws DataNotFoundException;

  List<TubeCustomMetadata> getTubeCustomMedataData(String tubeType) throws DataNotFoundException;

  List<TubeCustomMetadata> getTubeCustomMedataData();

  List<LotReceiptCustomMetadata> getLotReceiptCustomMetadata() throws DataNotFoundException;

  Integer updateUnattachedLaboratoryLastInsertion();

  List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadataOptions(String materialType) throws DataNotFoundException;

  List<MaterialReceiptCustomMetadata> getMaterialReceiptCustomMetadataOptions();
}
