package br.org.otus.laboratory.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
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

  Set<TubeDefinition> getDefaultTubeSet();

  Set<TubeDefinition> getTubeSetByGroupName(String setName);

  List<LabelReference> getLabelOrderByName(String orderName);

  List<String> generateCodes(TubeSeed seed);

  LaboratoryConfiguration getLaboratoryConfiguration();

  AliquotConfiguration getAliquotConfiguration();

  List<AliquoteDescriptor> getAliquotDescriptors();

  List<CenterAliquot> getAliquotDescriptorsByCenter(String center) throws DataNotFoundException;

  AliquoteDescriptor getAliquotDescriptorsByName(String name) throws DataNotFoundException;

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

  List<String> listPossibleExams(String center) throws DataNotFoundException;

    List<TubeCustomMetadata> get(String type) throws DataNotFoundException;
}
