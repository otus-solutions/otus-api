package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotCenterDescriptors;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.aliquot.CenterAliquot;
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
import org.powermock.reflect.Whitebox;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LaboratoryConfigurationServiceBean.class)
public class LaboratoryConfigurationServiceBeanTest {

  private static final String CENTER = "MG";
  private static final String TUBE_TYPE = "type";
  private static final Integer TUBE_DEFINITION_COUNT = 1;
  private static final String TUBE_DEFINITION_TYPE = "FLOURIDE";
  private static final String TUBE_DEFINITION_MOMENT = "POST OVERLOAD";

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
  private AliquoteDescriptor aliquoteDescriptor;


  @Before
  public void setup() throws Exception {
    when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
  }

  @Test
  public void getCheckingExist_method_should_call_getCheckingExist_dao_method() {
    laboratoryConfigurationServiceBean.getCheckingExist();
    verify(laboratoryConfigurationDao, Mockito.times(1)).getCheckingExist();
  }

  @Test
  public void getLaboratoryConfiguration_method_should_call_find_dao_method() throws DataNotFoundException {
    laboratoryConfigurationServiceBean.getLaboratoryConfiguration();
    verify(laboratoryConfigurationDao, Mockito.times(1)).find();
  }

  @Test
  public void getDefaultTubeSet_method_should_return_Default_TubeSet() throws DataNotFoundException {
    mock_laboratoryConfiguration_getCollectGroupConfiguration("DEFAULT", "DEFAULT");
    TubeDefinition result = laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get();
    assertEquals(TUBE_DEFINITION_COUNT, result.getCount());
    assertEquals(TUBE_DEFINITION_TYPE, result.getType());
    assertEquals(TUBE_DEFINITION_MOMENT, result.getMoment());
  }

  @Test
  public void getTubeSetByGroupName_method_should_TubeSet_by_GroupName() throws DataNotFoundException {
    mock_laboratoryConfiguration_getCollectGroupConfiguration(CENTER, "Center");
    TubeDefinition result = laboratoryConfigurationServiceBean.getTubeSetByGroupName(CENTER).stream().findFirst().get();
    assertEquals(TUBE_DEFINITION_COUNT, result.getCount());
    assertEquals(TUBE_DEFINITION_TYPE, result.getType());
    assertEquals(TUBE_DEFINITION_MOMENT, result.getMoment());
  }

  @Test
  public void getTubeSetByGroupName_method_should_return_list_void_case_not_find_GroupName() throws DataNotFoundException {
    mock_laboratoryConfiguration_getCollectGroupConfiguration(CENTER, "Center");
    assertEquals(new HashSet<>(), laboratoryConfigurationServiceBean.getTubeSetByGroupName("ES"));
  }

  @Test
  public void getLabelOrderByName_method_should_get_label_order_by_Name() throws DataNotFoundException {
    final String GROUP_NAME = "DEFAULT";
    final String TYPE = "GEL";
    final String MOMENT = "FASTING";
    final String REFERENCE = "QC_1";

    List<LabelReference> labelReference = new ArrayList<LabelReference>();
    labelReference.add(new LabelReference(GROUP_NAME, TYPE, MOMENT));

    HashMap<String, List<LabelReference>> orders = new HashMap<>();
    orders.put(REFERENCE, labelReference);
    LabelPrintConfiguration labelPrintConfiguration = new LabelPrintConfiguration(orders);
    when(laboratoryConfiguration.getLabelPrintConfiguration()).thenReturn(labelPrintConfiguration);

    LabelReference result = laboratoryConfigurationServiceBean.getLabelOrderByName(REFERENCE).stream().findFirst().get();
    assertEquals(GROUP_NAME, result.getGroupName());
    assertEquals(TYPE, result.getType());
    assertEquals(MOMENT, result.getMoment());
  }

  @Test
  public void generateCodes_method_generateCodes_should_use_correct_starting_point() throws DataNotFoundException {
    when(laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount())).thenReturn(startingPoint);
    when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);

    laboratoryConfigurationServiceBean.generateCodes(seed);

    verify(laboratoryConfigurationDao).updateLastTubeInsertion(seed.getTubeCount());
    verify(laboratoryConfiguration).generateNewCodeList(seed, ++startingPoint);
  }

  @Test
  public void generateCodes_method_should_return_generateNewCodeList_result() throws DataNotFoundException {
    List<String> codes = new ArrayList<>();
    when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    when(laboratoryConfigurationDao.updateLastTubeInsertion(seed.getTubeCount())).thenReturn(startingPoint);
    when(laboratoryConfiguration.generateNewCodeList(seed, ++startingPoint)).thenReturn(codes);
    assertEquals(codes, laboratoryConfigurationServiceBean.generateCodes(seed));
  }


  @Test
  public void getAliquotConfiguration_method_should_call_laboratoryConfigurationDao_to_list_the_centerAliquots() throws DataNotFoundException {
    AliquotConfiguration aliquotConfiguration = new AliquotConfiguration(null);
    when(laboratoryConfiguration.getAliquotConfiguration()).thenReturn(aliquotConfiguration);
    assertEquals(aliquotConfiguration, laboratoryConfigurationServiceBean.getAliquotConfiguration());
  }

  @Test
  public void getAliquotDescriptors_method_should_call_laboratoryConfigurationDao_to_list_the_AliquoteDescriptors() throws DataNotFoundException {
    mock_laboratoryConfiguration_getAliquotConfiguration();
    assertEquals(aliquoteDescriptor, laboratoryConfigurationServiceBean.getAliquotDescriptors().get(0));
  }

  @Test
  public void getAliquotDescriptorsByName_method_should_call_laboratoryConfigurationDao_to_list_the_AliquoteDescriptors_by_name() throws DataNotFoundException {
    mock_laboratoryConfiguration_getAliquotConfiguration();
    assertEquals(aliquoteDescriptor, laboratoryConfigurationServiceBean.getAliquotDescriptorsByName(CENTER));
  }

  @Test
  public void getAliquotDescriptorsByCenter_method_should_call_laboratoryConfigurationDao_to_list_the_centerAliquots() throws DataNotFoundException {
    List<CenterAliquot> centerAliquots = new ArrayList<>();

    AliquotCenterDescriptors first = PowerMockito.spy(new AliquotCenterDescriptors("", CENTER, new ArrayList<>(), null));
    when(first.getAllCenterAliquots()).thenReturn(centerAliquots);

    List<AliquotCenterDescriptors> aliquotCenterDescriptorsList = new ArrayList<>();
    aliquotCenterDescriptorsList.add(first);

    AliquotConfiguration aliquotConfiguration = new AliquotConfiguration(aliquotCenterDescriptorsList);
    when(laboratoryConfiguration.getAliquotConfiguration()).thenReturn(aliquotConfiguration);

    assertEquals(centerAliquots, laboratoryConfigurationServiceBean.getAliquotDescriptorsByCenter(CENTER));
  }

  @Test
  public void getAliquotExamCorrelation_method_should_call_laboratoryConfigurationDao_to_return_AliquotExamCorrelation() throws DataNotFoundException {
    AliquotExamCorrelation aliquotExamCorrelation = new AliquotExamCorrelation();
    when(laboratoryConfigurationDao.getAliquotExamCorrelation()).thenReturn(aliquotExamCorrelation);
    assertEquals(aliquotExamCorrelation, laboratoryConfigurationServiceBean.getAliquotExamCorrelation());
  }

  @Test
  public void listPossibleExams_method_should_call_laboratoryConfigurationDao_to_list_the_centerAliquots() throws DataNotFoundException {
    laboratoryConfigurationServiceBean.listPossibleExams(CENTER);
    verify(laboratoryConfigurationDao, Mockito.times(1)).listCenterAliquots(CENTER);
  }

  @Test
  public void listPossibleExams_method_should_call_laboratoryConfigurationDao_to_listPossibleExams_passing_the_centerAliquots() throws DataNotFoundException {
    ArrayList arrayList = new ArrayList();
    when(laboratoryConfigurationDao.listCenterAliquots(CENTER)).thenReturn(arrayList);
    laboratoryConfigurationServiceBean.listPossibleExams(CENTER);
    verify(laboratoryConfigurationDao, Mockito.times(1)).getAliquotsExams(arrayList);
  }

  @Test
  public void getTubeCustomMedataData_method_should_invoke_dao_method() throws DataNotFoundException {
    laboratoryConfigurationServiceBean.getTubeCustomMedataData(TUBE_TYPE);
    verify(laboratoryConfigurationDao, Mockito.times(1)).getTubeCustomMedataData(TUBE_TYPE);
  }

  @Test
  public void updateUnattachedLaboratoryLastInsertion_method_should_invoke_dao_method() {
    final Integer LAST_INSERTION = 1;
    when(laboratoryConfigurationDao.updateUnattachedLaboratoryLastInsertion()).thenReturn(LAST_INSERTION);
    assertEquals(LAST_INSERTION, laboratoryConfigurationServiceBean.updateUnattachedLaboratoryLastInsertion());
  }


  private void mock_laboratoryConfiguration_getCollectGroupConfiguration(String collectGroupDescriptorName, String collectGroupDescriptorType){
    Set<TubeDefinition> tubeSet = new HashSet<>();
    tubeSet.add(new TubeDefinition(TUBE_DEFINITION_COUNT, TUBE_DEFINITION_TYPE, TUBE_DEFINITION_MOMENT));

    Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
    collectGroupDescriptor.add(new CollectGroupDescriptor(collectGroupDescriptorName, collectGroupDescriptorType, tubeSet));
    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
    when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);
  }

  private void mock_laboratoryConfiguration_getAliquotConfiguration(){
    aliquoteDescriptor = new AliquoteDescriptor("", CENTER, "", "", 1);

    AliquotConfiguration aliquotConfiguration = new AliquotConfiguration(null);
    Whitebox.setInternalState(aliquotConfiguration, "aliquotDescriptors", new ArrayList<AliquoteDescriptor>(){{
      add(aliquoteDescriptor);
    }});

    when(laboratoryConfiguration.getAliquotConfiguration()).thenReturn(aliquotConfiguration);
  }

}
