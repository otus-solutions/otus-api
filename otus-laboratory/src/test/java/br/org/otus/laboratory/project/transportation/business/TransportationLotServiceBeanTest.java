package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import br.org.otus.laboratory.project.transportation.validators.TransportationLotValidator;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TransportationLotServiceBean.class)
public class TransportationLotServiceBeanTest {

    private static String ALIQUOT_CODE = "1221654877";
    private static String ALIQUOT_CODE_JSON = "{code:1221654877}";

    @InjectMocks
    private TransportationLotServiceBean transportationLotServiceBean = PowerMockito.spy(new TransportationLotServiceBean());

    @InjectMocks
    private TransportationLotServiceBean transportationLotServiceBean2;

    @Mock
    private TransportationLotDao transportationLotDao;

    @Mock
    private WorkAliquotFiltersDTO workAliquotFiltersDTO;

    @Mock
    private TransportationLot transportationLot;

    @Mock
    private TransportationLotValidator transportationLotValidator;

    @Test
    public void method_create_should_call_validateLot() throws Exception {
        transportationLotServiceBean.create(transportationLot,"a");
        PowerMockito.verifyPrivate(transportationLotServiceBean).invoke("_validateLot",transportationLot);
    }

    @Test
    public void method_update_should_call_validateLot() throws Exception {
        transportationLotServiceBean.update(transportationLot);
        PowerMockito.verifyPrivate(transportationLotServiceBean).invoke("_validateLot",transportationLot);
    }

    @Test
    public void method_getAliquot_should_call_CheckTransportedAliquot() throws ValidationException {
        workAliquotFiltersDTO = WorkAliquotFiltersDTO.deserialize(ALIQUOT_CODE_JSON);
        transportationLotServiceBean.getAliquot(workAliquotFiltersDTO);
        verify(transportationLotDao).checkIfTransported(ALIQUOT_CODE);
    }
}
