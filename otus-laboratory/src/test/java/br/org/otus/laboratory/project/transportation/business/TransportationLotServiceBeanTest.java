package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TransportationLotServiceBean.class)
public class TransportationLotServiceBeanTest {

    private static final String USER_EMAIL = "otus@otus.com";
    private static final String LOTE_CODE = "888888";
    private static String ALIQUOT_CODE = "1221654877";
    private static String ALIQUOT_CODE_JSON = "{code:1221654877}";

    @InjectMocks
    private TransportationLotServiceBean transportationLotServiceBean = PowerMockito.spy(new TransportationLotServiceBean());
    @Mock
    private TransportationLotDao transportationLotDao;
    @Mock
    private TransportationLot transportationLot;
    @Mock
    private TransportationLot oldTransportationLot;
    @Mock
    private AliquotDao aliquotDao;
    @Mock
    private TransportationLot updateResult;

    ObjectId transportationLotId = new ObjectId();
    private ArrayList<String> aliquotCodeList;


    @Test
    public void createMethod_should_return_instance_of_TransportationLot() throws Exception {
        aliquotCodeList = new ArrayList<>();
        aliquotCodeList.add(ALIQUOT_CODE);
        when(transportationLot.getAliquotCodeList()).thenReturn(aliquotCodeList);
        when(transportationLotDao.persist(transportationLot)).thenReturn(transportationLotId);

        assertTrue(transportationLotServiceBean.create(transportationLot, USER_EMAIL) instanceof TransportationLot);
        verifyPrivate(transportationLotServiceBean, Mockito.times(1)).invoke("_validateLot",transportationLot);
        verify(transportationLot, times(1)).setOperator(USER_EMAIL);
        verify(transportationLot, times(1)).getAliquotCodeList();
        verify(transportationLotDao, times(1)).persist(transportationLot);
        verify(aliquotDao, times(1)).addToTransportationLot(aliquotCodeList, transportationLotId);
    }

    @Test
    public void updateMethod_should_return_result_of_update() throws DataNotFoundException, ValidationException {
        when(transportationLot.getCode()).thenReturn(LOTE_CODE);
        when(transportationLotDao.findByCode(LOTE_CODE)).thenReturn(oldTransportationLot);
        when(transportationLotDao.update(transportationLot)).thenReturn(updateResult);

        assertTrue(transportationLotServiceBean.update(transportationLot) instanceof TransportationLot);
        verify(aliquotDao, Mockito.times(1)).updateTransportationLotId(Mockito.any(),Mockito.any());
    }

    @Test
    public void listMethod_should_invoke_find_of_LotDao() {
        transportationLotServiceBean.list();
        verify(transportationLotDao, Mockito.times(1)).find();
    }

    @Test
    public void deleteMethod_should_invoke_delete_of_LotDao() throws DataNotFoundException {
        when(transportationLotDao.findByCode(LOTE_CODE)).thenReturn(transportationLot);
        transportationLotServiceBean.delete(LOTE_CODE);
        verify(transportationLotDao, times(1)).delete(LOTE_CODE);
        verify(aliquotDao, times(1)).updateTransportationLotId(any(),any());
    }

    @Test
    public void method_create_should_call_validateLot() throws Exception {
        transportationLotServiceBean.create(transportationLot,"a");
        verifyPrivate(transportationLotServiceBean).invoke("_validateLot",transportationLot);
    }
}
