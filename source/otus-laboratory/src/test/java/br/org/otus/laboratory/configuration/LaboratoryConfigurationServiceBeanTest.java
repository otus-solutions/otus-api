package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.label.LabelPrintConfiguration;
import br.org.otus.laboratory.configuration.label.LabelReference;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LaboratoryConfigurationServiceBean.class)
public class LaboratoryConfigurationServiceBeanTest {

  private static final String CENTER = "MG";
  private static final String TUBE_TYPE = "type";


  @InjectMocks
  private LaboratoryConfigurationServiceBean laboratoryConfigurationServiceBean;

  @Mock
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  @Mock
  private LaboratoryConfiguration laboratoryConfiguration;

  @Mock
  private CodeConfiguration codeConfiguration;

  @Mock
  private TubeSeed seed;

  private Integer startingPoint = 2;

  @Before
  public void setup() throws Exception {
    PowerMockito.when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
  }

  @Test
  public void getHeaders_method_should_call_getHeaders_method() {
    laboratoryConfigurationServiceBean.getCheckingExist();

    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).getCheckingExist();
  }

  @Test
  public void should_to_return_get_Default_TubeSet() throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = new HashSet<>();
    tubeSet.add(new TubeDefinition(1, "FLOURIDE", "POST OVERLOAD"));

    Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
    collectGroupDescriptor.add(new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSet));
    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
    PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);

    TubeDefinition tube = new TubeDefinition(1, "FLOURIDE", "POST OVERLOAD");
    assertEquals(tube.getCount(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getCount());
    assertEquals(tube.getType(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getType());
    assertEquals(tube.getMoment(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getMoment());
  }

  @Test
  public void should_to_return_get_TubeSet_By_GroupName() throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = new HashSet<>();
    tubeSet.add(new TubeDefinition(1, "CITRATE", "NONE"));

    Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
    collectGroupDescriptor.add(new CollectGroupDescriptor(CENTER, "Center", tubeSet));
    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
    PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);


    TubeDefinition expectedTubeSet = new TubeDefinition(1, "CITRATE", "NONE");
    assertEquals(expectedTubeSet.getCount(), laboratoryConfigurationServiceBean.getTubeSetByGroupName(CENTER).stream().findFirst().get().getCount());
    assertEquals(expectedTubeSet.getType(), laboratoryConfigurationServiceBean.getTubeSetByGroupName(CENTER).stream().findFirst().get().getType());
    assertEquals(expectedTubeSet.getMoment(), laboratoryConfigurationServiceBean.getTubeSetByGroupName(CENTER).stream().findFirst().get().getMoment());

  }

  @Test
  public void should_to_return_list_void_case_not_find_GroupName() throws DataNotFoundException {
    Set<TubeDefinition> tubeSet = new HashSet<>();
    tubeSet.add(new TubeDefinition(1, "CITRATE", "NONE"));

    Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
    collectGroupDescriptor.add(new CollectGroupDescriptor(CENTER, "Center", tubeSet));
    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
    PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);


    Set<TubeDefinition> expectedTubeSet = new HashSet<>();
    assertEquals(expectedTubeSet, laboratoryConfigurationServiceBean.getTubeSetByGroupName("ES"));
  }

  @Test
  public void should_get_label_order_by_Name() {
    String reference = "QC_1";
    List<LabelReference> labelReference = new ArrayList<LabelReference>();
    labelReference.add(new LabelReference("DEFAULT", "GEL", "FASTING"));

    HashMap<String, List<LabelReference>> orders = new HashMap<>();
    orders.put(reference, labelReference);
    LabelPrintConfiguration labelPrintConfiguration = new LabelPrintConfiguration(orders);
    PowerMockito.when(laboratoryConfiguration.getLabelPrintConfiguration()).thenReturn(labelPrintConfiguration);

    List<LabelReference> expectedLabelReference = new ArrayList<LabelReference>();
    expectedLabelReference.add(new LabelReference("DEFAULT", "GEL", "FASTING"));

    assertEquals(expectedLabelReference.stream().findFirst().get().getGroupName(),
      laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getGroupName());
    assertEquals(expectedLabelReference.stream().findFirst().get().getType(),
      laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getType());
    assertEquals(expectedLabelReference.stream().findFirst().get().getMoment(),
      laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getMoment());

  }

  @Test
  public void method_generateCodes_should_use_correct_starting_point() {
    Mockito.when(laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount())).thenReturn(startingPoint);
    Mockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);

    laboratoryConfigurationServiceBean.generateCodes(seed);

    Mockito.verify(laboratoryConfigurationDao).updateLastTubeInsertion(seed.getTubeCount());
    Mockito.verify(laboratoryConfiguration).generateNewCodeList(seed, ++startingPoint);
  }

  @Test
  public void method_generateCodes_should_return_generateNewCodeList_result() {
    List<String> codes = new ArrayList<>();
    codes.add("33100031");
    codes.add("33100032");

    Mockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    Mockito.when(laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount())).thenReturn(startingPoint);
    Mockito.when(laboratoryConfiguration.generateNewCodeList(seed, ++startingPoint)).thenReturn(codes);

    List<String> expectedCodes = new ArrayList<>();
    expectedCodes.add("33100031");
    expectedCodes.add("33100032");
    assertEquals(expectedCodes, laboratoryConfigurationServiceBean.generateCodes(seed));
  }

  @Test
  public void should_call_laboratoryConfigurationDao_to_list_the_centerAliquots() throws DataNotFoundException {
    laboratoryConfigurationServiceBean.listPossibleExams(CENTER);
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).listCenterAliquots(CENTER);
  }

  @Test
  public void should_call_laboratoryConfigurationDao_to_listPossibleExams_passing_the_centerAliquots() throws DataNotFoundException {
    ArrayList arrayList = new ArrayList();
    Mockito.when(laboratoryConfigurationDao.listCenterAliquots(CENTER)).thenReturn(arrayList);

    laboratoryConfigurationServiceBean.listPossibleExams(CENTER);
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).getAliquotsExams(arrayList);
  }

  @Test
  public void getTubeCustomMedataData_method_should_invoke_dao_method() throws DataNotFoundException {
    laboratoryConfigurationServiceBean.getTubeCustomMedataData(TUBE_TYPE);
    Mockito.verify(laboratoryConfigurationDao, Mockito.times(1)).getTubeCustomMedataData(TUBE_TYPE);
  }

}
