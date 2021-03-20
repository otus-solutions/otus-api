package br.org.otus.laboratory.project.transportation;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
public class TransportationLotTest {

  private static final String OBJECT_TYPE = "TransportationLot";
  private static final String LOTE_CODE = "77777";
  private static final String USER_MAIL = "otus@otus.com";
  private static final String ALIQUOT_CODE = "300400500";
  private static final String DATE = "2018-11-12T16:28:00";


  private TransportationLot transportationLot = new TransportationLot();
  @Mock
  private Aliquot aliquot = spy(new Aliquot());
  @Mock
  private FieldCenter fieldCenter;
  private ArrayList<Aliquot> aliquotList = new ArrayList<>();
  private ObjectId objectId;

  @Before
  public void setUp() throws Exception {
    objectId = new ObjectId();
    setInternalState(transportationLot, "_id", objectId);
    transportationLot.setCode(LOTE_CODE);
    setInternalState(aliquot, "code", "300400500");
    aliquotList.add(aliquot);
    setInternalState(transportationLot, "aliquotList", aliquotList);
    transportationLot.setOperator(USER_MAIL);
    transportationLot.setCenter(fieldCenter);
    setInternalState(transportationLot, "shipmentDate", LocalDateTime.parse(DATE));
    setInternalState(transportationLot, "processingDate", LocalDateTime.parse(DATE));
  }

  @Test
  public void unitTest_for_evoke_getters() {
    assertEquals(OBJECT_TYPE, transportationLot.getObjectType());
    assertEquals(LOTE_CODE, transportationLot.getCode());
    assertTrue(transportationLot.getAliquotList().contains(aliquot));
    assertEquals(USER_MAIL, transportationLot.getOperator());
    assertEquals(fieldCenter, transportationLot.getCenter());
    assertEquals(USER_MAIL, transportationLot.getOperator());
    assertEquals(LocalDateTime.parse(DATE), transportationLot.getShipmentDate());
    assertEquals(LocalDateTime.parse(DATE), transportationLot.getProcessingDate());
  }

  @Test
  public void getAliquotCodeList_should_return_codeList() {
    assertTrue(transportationLot.getAliquotCodeList().contains(ALIQUOT_CODE));
  }

  @Test
  public void getLotId_should_return_Id_of_lot() {
    assertTrue(transportationLot.getLotId() instanceof ObjectId);
  }
}