package br.org.otus.laboratory.project;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TransportationLotDaoBean.class, TransportationLot.class })
public class TransportationLotDaoBeanTest {

  @InjectMocks
  private TransportationLotDaoBean transportationLotDaoBean = PowerMockito.spy(new TransportationLotDaoBean());
  @Mock
  private MongoCollection<Document> collection;
  @Mock
  private FindIterable<Document> result;
  @Mock
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  private MongoCursor cursor = PowerMockito.mock(MongoCursor.class);

  @Before
  public void setup() {
    Whitebox.setInternalState(transportationLotDaoBean, "collection", collection);
    Whitebox.setInternalState(transportationLotDaoBean, "laboratoryConfigurationDao", laboratoryConfigurationDao);
  }

  @Test
  public void method_persist_should_call_method_setCode() throws Exception {
    TransportationLot transportationLot = Mockito.mock(TransportationLot.class);
    Mockito.when(this.laboratoryConfigurationDao.createNewLotCodeForExam()).thenReturn(Matchers.anyString());
    PowerMockito.when(TransportationLot.class, "serialize", Mockito.any()).thenReturn(Matchers.anyString());

    transportationLotDaoBean.persist(transportationLot);

    Mockito.verify(transportationLot, Mockito.times(1)).setCode(Matchers.anyString());
  }

  @Ignore
  @Test
  public void method_find_should_call_method_find_in_collection() {
    Mockito.when(this.collection.find(Matchers.<Bson>any())).thenReturn(result);
    Mockito.when(result.projection(Matchers.<Bson>any())).thenReturn(result);
    Mockito.when(result.iterator()).thenReturn(this.cursor);
    Mockito.when(TransportationLot.deserialize(Matchers.anyString())).thenReturn(Matchers.any());

    transportationLotDaoBean.find();

    Mockito.verify(this.collection, Mockito.times(1)).find();
  }

}
