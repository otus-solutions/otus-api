package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CenterTubeGeneratorTest {

  private static final String FIELD_CENTER_ACRONYM = "RS";
  private static final String COLLECT_GROUP_DESCRIPTOR_NAME = "collect_group";
  private static final String COLLECT_GROUP_DESCRIPTOR_TYPE = "collect_group";
  private static final String TUBE_TYPE = "GEL";
  private static final String TUBE_MOMENT = "POST OVERLOAD";
  private static final Integer TUBE_COUNT = 1;

  @InjectMocks
  private CenterTubeGenerator centerTubeGenerator;
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;

  private Set<TubeDefinition> tubeDefinitions;
  private CollectGroupDescriptor collectGroupDescriptor;
  private TubeSeed tubeSeed;


  @Before
  public void setUp() throws DataNotFoundException {
    FieldCenter fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(FIELD_CENTER_ACRONYM);

    tubeDefinitions = new HashSet<>();
    tubeDefinitions.add(new TubeDefinition(TUBE_COUNT, TUBE_TYPE, TUBE_MOMENT));
    collectGroupDescriptor = new CollectGroupDescriptor(COLLECT_GROUP_DESCRIPTOR_NAME, COLLECT_GROUP_DESCRIPTOR_TYPE, tubeDefinitions);
    tubeSeed = TubeSeed.generate(fieldCenter, collectGroupDescriptor);
    PowerMockito.when(laboratoryConfigurationService.getTubeSetByGroupName(FIELD_CENTER_ACRONYM)).thenReturn(tubeDefinitions);
  }

  @Test
  public void method_should_return_GROUP_NAME_DEFAULT() throws DataNotFoundException {
    assertEquals(FIELD_CENTER_ACRONYM,
      centerTubeGenerator.getTubeDefinitions(tubeSeed).stream().findAny().get().getGroup());
  }

}
