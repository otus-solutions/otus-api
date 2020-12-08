package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class QualityControlTubeGeneratorTest {

  private static final Integer TUBE_1_COUNT = 4;
  private static final Integer TUBE_2_COUNT = 2;
  private static final String TUBE_1_TYPE = "GEL";
  private static final String TUBE_2_TYPE = "FLOURIDE";
  private static final String TUBE_1_MOMENT = "POST_OVERLOAD";
  private static final String TUBE_2_MOMENT = "FASTING";
  private static final String DEFAULT = "DEFAULT";

  @InjectMocks
  private QualityControlTubeGenerator qualityControlTubeGenerator;
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;

  private CollectGroupDescriptor collectGroupDescriptor;
  private Set<TubeDefinition> tubeSets;
  private TubeSeed tubeSeed;

  @Before
  public void setUp() throws DataNotFoundException {
    tubeSets = new HashSet<>();
    tubeSets.add(new TubeDefinition(TUBE_1_COUNT, TUBE_1_TYPE, TUBE_1_MOMENT));
    tubeSets.add(new TubeDefinition(TUBE_2_COUNT, TUBE_2_TYPE, TUBE_2_MOMENT));

    FieldCenter fieldCenter = new FieldCenter();
    collectGroupDescriptor = new CollectGroupDescriptor(DEFAULT, DEFAULT, tubeSets);
    tubeSeed = TubeSeed.generate(fieldCenter, collectGroupDescriptor);

    when(laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getCollectGroupDescriptor().getName()))
      .thenReturn(tubeSets);
  }

  @Test
  public void getTubeDefinitions_method_should_call_getTubeSetByGroupName() throws DataNotFoundException {
    qualityControlTubeGenerator.getTubeDefinitions(tubeSeed);
    verify(laboratoryConfigurationService).getTubeSetByGroupName(DEFAULT);
  }

  @Test
  public void getTubeDefinitions_method_check() throws DataNotFoundException {
    List<TubeDefinition> tubeDefinitions = qualityControlTubeGenerator.getTubeDefinitions(tubeSeed);
    assertEquals(tubeSets.size(), tubeDefinitions.size());

    assertEquals(TUBE_1_TYPE, tubeDefinitions.get(0).getType());
    assertEquals(TUBE_1_MOMENT, tubeDefinitions.get(0).getMoment());

    assertEquals(TUBE_2_TYPE, tubeDefinitions.get(1).getType());
    assertEquals(TUBE_2_MOMENT, tubeDefinitions.get(1).getMoment());
  }

  @Test
  public void getTubeDefinitions_method_should_return_empty_list() throws DataNotFoundException {
    tubeSets.clear();
    assertEquals(0, qualityControlTubeGenerator.getTubeDefinitions(tubeSeed).size());
  }

  @Test
  public void method_should_getQuantityTubeDefinition() throws DataNotFoundException {
    assertEquals(tubeSets.size(), qualityControlTubeGenerator.getTubeDefinitions(tubeSeed).size());
  }

  @Test
  public void method_should_sumGetTubes() throws DataNotFoundException {
    qualityControlTubeGenerator.getTubeDefinitions(tubeSeed);
    assertEquals(TUBE_1_COUNT + TUBE_2_COUNT, (int) tubeSeed.getTubeCount());
  }
}
