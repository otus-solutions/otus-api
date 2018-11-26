package br.org.otus.laboratory.project;

//TODO: AO-92 refactor test
//import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//
//import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
//import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
//import br.org.otus.laboratory.project.transportation.TransportationLot;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ TransportationLotDaoBean.class, TransportationLot.class })
//public class TransportationLotDaoBeanTest {
//
//  @InjectMocks
//  private TransportationLotDaoBean transportationLotDaoBean = PowerMockito.spy(new TransportationLotDaoBean());
//  @Mock
//  private MongoCollection<Document> collection;
//  @Mock
//  private FindIterable<Document> result;
//  @Mock
//  private LaboratoryConfigurationDao laboratoryConfigurationDao;
//  @Mock
//  private TransportationAliquotFiltersDTO transportationAliquotFiltersDTO;
//  @Mock
//  private ParticipantLaboratoryDao participantLaboratoryDao;
//
//  private MongoCursor cursor = PowerMockito.mock(MongoCursor.class);
//
//  @Before
//  public void setup() {
//    Whitebox.setInternalState(transportationLotDaoBean, "collection", collection);
//    Whitebox.setInternalState(transportationLotDaoBean, "laboratoryConfigurationDao", laboratoryConfigurationDao);
//    Whitebox.setInternalState(transportationLotDaoBean, "participantLaboratoryDao", participantLaboratoryDao);
//  }
//
//  @Ignore
//  @Test
//  public void method_persist_should_call_method_setCode() throws Exception {
//    TransportationLot transportationLot = Mockito.mock(TransportationLot.class);
//    Mockito.when(this.laboratoryConfigurationDao.createNewLotCodeForExam()).thenReturn(Matchers.anyString());
//    PowerMockito.when(TransportationLot.class, "serialize", Mockito.any()).thenReturn(Matchers.anyString());
//
//    transportationLotDaoBean.persist(transportationLot);
//
//    Mockito.verify(transportationLot, Mockito.times(1)).setCode(Matchers.anyString());
//  }
//
//  @Ignore
//  @Test
//  public void method_find_should_call_method_find_in_collection() {
//    Mockito.when(this.collection.find(Matchers.<Bson>any())).thenReturn(result);
//    Mockito.when(result.projection(Matchers.<Bson>any())).thenReturn(result);
//    Mockito.when(result.iterator()).thenReturn(this.cursor);
//    Mockito.when(TransportationLot.deserialize(Matchers.anyString())).thenReturn(Matchers.any());
//
//    transportationLotDaoBean.find();
//
//    Mockito.verify(this.collection, Mockito.times(1)).find();
//  }
//
//  @Test
//  public void method_getAliquotsByPeriod_should_call_method_getAliquotsByPeriod() throws DataNotFoundException {
//    transportationLotDaoBean.getAliquotsByPeriod(transportationAliquotFiltersDTO);
//
//    Mockito.verify(participantLaboratoryDao, Mockito.times(1)).getAliquotsByPeriod(transportationAliquotFiltersDTO);
//  }
//
//  @Test
//  public void method_getAliquot_should_call_method_getAliquot() throws DataNotFoundException {
//    transportationLotDaoBean.getAliquot(transportationAliquotFiltersDTO);
//
//    Mockito.verify(participantLaboratoryDao, Mockito.times(1)).getAliquot(transportationAliquotFiltersDTO);
//  }
//
//}
