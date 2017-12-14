package br.org.otus.laboratory.project.exam.validators;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExamLotValidator.class)
public class ExamLotValidatorTest {

	private static final String FIELD_CENTER_RS = "RS";
	private static final String BIOCHEMICAL_SERUM = "BIOCHEMICAL_SERUM";
	private static final String BIOCHEMICAL_SERUM_FALSE = "BIOCHEMICAL_SERUM_FALSE";

	@Mock
	private ExamLotDao examLotDao;
	@Mock
	private TransportationLotDao transportationLotDao;
	@Mock
	private WorkAliquot workAliquot;
	@Mock
	private FieldCenter fieldCenter;
	private ExamLot examLot = spy(new ExamLot());
	private ExamLotValidator examLotValidator;
	
	private List<WorkAliquot> aliquotList;
	private List<ExamLot> examLotList;
	private Optional<ExamLot> searchedAliquot;

	@Before
	public void setUp() throws Exception {
		examLotValidator = spy(new ExamLotValidator(examLotDao, transportationLotDao, examLot));
		aliquotList = new ArrayList<>();
		examLotList = new ArrayList<>();
	}

	@Ignore
	@Test
	public void method_validate_should_check_call_validation_methods() throws Exception {
		aliquotList.add(workAliquot);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLotDao.getAliquots()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(workAliquot.getName()).thenReturn(BIOCHEMICAL_SERUM);

		examLotValidator.validate();

		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkIfAliquotsExist");
		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkOfTypesInLot");
		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkForAliquotsOnAnotherLots");
		Mockito.verify(examLotDao, times(1)).getAliquots();
		Mockito.verify(examLot, times(3)).getAliquotList();
		Mockito.verify(workAliquot, times(1)).getName();
		Mockito.verify(examLot, times(1)).getAliquotName();
	}
	
	@Test
	public void when_the_checkIfAliquotsExist_method_is_called_then_it_should_call_method_getAliquotList() throws Exception {
		aliquotList.add(workAliquot);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLotDao.getAliquots()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(workAliquot.getName()).thenReturn(BIOCHEMICAL_SERUM);
		
		examLotValidator.validate();
		
		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkIfAliquotsExist");
		Mockito.verify(examLot, times(3)).getAliquotList();
	}

	@Test(expected = ValidationException.class)
	public void method_validate_should_throw_ValidationException_for_Aliquots_not_found() throws ValidationException {
		aliquotList.add(workAliquot);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		examLotValidator.validate();
	}

	@Test(expected = ValidationException.class)
	public void method_validate_should_throw_ValidationException_for_there_are_different_types_of_aliquots_in_lot()
			throws DataNotFoundException, ValidationException {
		aliquotList.add(workAliquot);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLotDao.getAliquots()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(workAliquot.getName()).thenReturn(BIOCHEMICAL_SERUM_FALSE);
		examLotValidator.validate();
	}
}
