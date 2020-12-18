package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeGenerator;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.CenterGenerator;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.DefaultGenerator;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.QualityControlGenerator;
import br.org.otus.laboratory.configuration.label.LabelReference;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class TubeServiceBeanTest {

  private static final String FIELD_CENTER_ACRONYM = "RS";
  private static final String COLLECT_GROUP_NAME = "collect_group";
  private static final String TUBE_TYPE = "type";
  private static final String TUBE_MOMENT = "POST OVERLOAD";
  private static final String TUBE_CODE = "331005009";

  @InjectMocks
  private TubeServiceBean tubeServiceBean;

  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;
  @Mock
  @DefaultGenerator
  private TubeGenerator defaultTubeGenerator;
  @Mock
  @QualityControlGenerator
  private TubeGenerator qualityControlTubeGenerator;
  @Mock
  @CenterGenerator
  private TubeGenerator centerTubeGenerator;

  private TubeSeed tubeSeed;
  private List<Tube> tubes;
  private CollectGroupDescriptor collectGroupDescriptor = PowerMockito.spy(new CollectGroupDescriptor(COLLECT_GROUP_NAME, "", new HashSet<>()));
  private FieldCenter fieldCenter = new FieldCenter();

  @Before
  public void setUp(){
    Whitebox.setInternalState(tubeServiceBean, "defaultTubeGenerator", defaultTubeGenerator);
    Whitebox.setInternalState(tubeServiceBean, "qualityControlTubeGenerator", qualityControlTubeGenerator);
    Whitebox.setInternalState(tubeServiceBean, "centerTubeGenerator", centerTubeGenerator);

    fieldCenter.setAcronym(FIELD_CENTER_ACRONYM);
    tubeSeed = PowerMockito.spy(TubeSeed.generate(fieldCenter, collectGroupDescriptor));
    tubes = new ArrayList<Tube>(){{
      add(new Tube(TUBE_TYPE, TUBE_MOMENT, TUBE_CODE, COLLECT_GROUP_NAME));
    }};
  }


  @Test
  public void generateTubes_method() throws DataNotFoundException {
    when(defaultTubeGenerator.generateTubes(tubeSeed)).thenReturn(tubes);
    when(qualityControlTubeGenerator.generateTubes(tubeSeed)).thenReturn(tubes);
    when(centerTubeGenerator.generateTubes(tubeSeed)).thenReturn(tubes);
    when(laboratoryConfigurationService.getLabelOrderByName(COLLECT_GROUP_NAME)).thenReturn(new ArrayList<LabelReference>(){{
      add(new LabelReference(COLLECT_GROUP_NAME, TUBE_TYPE, TUBE_MOMENT));
    }});
    assertEquals(3, tubeServiceBean.generateTubes(tubeSeed).size());
  }
}
