package br.org.otus.laboratory.project.exam.validators;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examLot.validators.ExamLotValidator;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExamLotValidator.class)
public class ExamLotValidatorTest {
	private static final String FIELD_CENTER_RS = "RS";
	private static final String FIELD_CENTER_RJ = "RJ";

	private static final String BIOCHEMICAL_SERUM = "BIOCHEMICAL_SERUM";
	private static final String FASTING_HORMONE_CENTRAL = "FASTING_HORMONE_CENTRAL";

	private static final String MESSAGE_NOT_FOUND = "Aliquots not found";
	private static final String MESSAGE_DIFFERENT_TYPES = "There are different types of aliquots in lot";
	private static final String MESSAGE_ALIQUOTS_IN_ANOTHER_LOT = "There are aliquots in another lot";
	private static final String MESSAGE_DIFFERENT_THE_CENTER_AND_NOT_EXIST_IN_TRASNPORTATION = "There are aliquots different from the center and not exist in lot of transport";

	@Mock
	private ExamLotDao examLotDao;
	@Mock
	private TransportationLotDao transportationLotDao;
    @Mock
    private AliquotDao aliquotDao;
	@Mock
	private Aliquot aliquot1;
	@Mock
	private Aliquot aliquot2;
	@Mock
	private FieldCenter fieldCenter1;
	@Mock
	private FieldCenter fieldCenter2;

	private ExamLot examLot = spy(new ExamLot());
	private ExamLot otherLot = spy(new ExamLot());

	private ExamLotValidator examLotValidator;

	@Before
	public void setUp() throws Exception {
		examLotValidator = spy(new ExamLotValidator(examLotDao, transportationLotDao, examLot, aliquotDao));
	}

	@Test
	public void method_validate_should_called_methods_of_validation() throws Exception {
		when(aliquot1.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot1.getCode()).thenReturn("354005012");
		when(aliquot1.getFieldCenter()).thenReturn(fieldCenter1);
		when(fieldCenter1.getAcronym()).thenReturn(FIELD_CENTER_RS);

		when(aliquot2.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot2.getCode()).thenReturn("354005012");

		List<Aliquot> aliquotList = new ArrayList<>();
		aliquotList.add(aliquot1);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(examLot.getFieldCenter()).thenReturn(fieldCenter1);
		when(fieldCenter1.getAcronym()).thenReturn(FIELD_CENTER_RS);

		List<Aliquot> resultDao = new ArrayList<>();
		resultDao.add(aliquot2);
		when(aliquotDao.getAliquots()).thenReturn(resultDao);

		examLotValidator.validate();

		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkIfAliquotsExist");
		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkOfTypesInLot");
		PowerMockito.verifyPrivate(examLotValidator, times(1)).invoke("checkForAliquotsOnAnotherLots");
	}

	@Test
	public void method_validate_should_throw_ValidationException_for_Aliquots_not_found() throws DataNotFoundException {
		when(aliquot1.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot1.getCode()).thenReturn("354005012");

		List<Aliquot> aliquotList = new ArrayList<>();
		aliquotList.add(aliquot1);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(examLot.getAliquotList()).thenReturn(aliquotList);

		List<Aliquot> resultDao = new ArrayList<>();
		when(aliquotDao.getAliquots()).thenReturn(resultDao);

		try {
			examLotValidator.validate();
			fail();
		} catch (ValidationException expected) {
			assertThat(expected.getMessage(), CoreMatchers.containsString(MESSAGE_NOT_FOUND));
		}
	}

	@Test
	public void method_validate_should_return_exception_when_different_aliquots_in_lot() throws DataNotFoundException {
		when(aliquot1.getCode()).thenReturn("354005012");
		when(aliquot1.getName()).thenReturn(FASTING_HORMONE_CENTRAL);

		List<Aliquot> aliquotList = new ArrayList<>();
		aliquotList.add(aliquot1);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);

		when(aliquotDao.getAliquots()).thenReturn(aliquotList);

		try {
			examLotValidator.validate();
			fail();
		} catch (ValidationException expected) {
			assertThat(expected.getMessage(), CoreMatchers.containsString(MESSAGE_DIFFERENT_TYPES));
		}
	}

	@Test
	public void method_validate_should_return_exception_when_exist_aliquots_duplicate_in_DB()
			throws DataNotFoundException {
		when(aliquot1.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot1.getCode()).thenReturn("354005012");

		when(aliquot2.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot2.getCode()).thenReturn("354005012");

		List<Aliquot> aliquotList = new ArrayList<>();
		aliquotList.add(aliquot1);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);

		when(otherLot.getAliquotList()).thenReturn(aliquotList);

		List<Aliquot> resultFindAliquotsInDB = new ArrayList<>();
		resultFindAliquotsInDB.add(aliquot2);
		when(aliquotDao.getAliquots()).thenReturn(resultFindAliquotsInDB);

		List<ExamLot> resultFindExamInDB = new ArrayList<>();
		resultFindExamInDB.add(otherLot);
		when(examLotDao.find(anyString())).thenReturn(resultFindExamInDB);

		try {
			examLotValidator.validate();
			fail();
		} catch (ValidationException expected) {
			assertThat(expected.getMessage(), CoreMatchers.containsString(MESSAGE_ALIQUOTS_IN_ANOTHER_LOT));
		}
	}

	@Test
	public void method_validate_should_return_exception_when_aliquots_different_from_the_center_an_not_exist_in_lot_of_transport()
			throws DataNotFoundException {
		when(aliquot1.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot1.getCode()).thenReturn("354005012");
		when(aliquot1.getFieldCenter()).thenReturn(fieldCenter1);
		when(fieldCenter1.getAcronym()).thenReturn(FIELD_CENTER_RS);

		when(aliquot2.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(aliquot2.getCode()).thenReturn("354005012");

		List<Aliquot> aliquotList = new ArrayList<>();
		aliquotList.add(aliquot1);
		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(examLot.getFieldCenter()).thenReturn(fieldCenter2);
		when(fieldCenter2.getAcronym()).thenReturn(FIELD_CENTER_RJ);

		List<Aliquot> resultDao = new ArrayList<>();
		resultDao.add(aliquot2);
		when(aliquotDao.getAliquots()).thenReturn(resultDao);

		try {
			examLotValidator.validate();
			fail();
		} catch (ValidationException expected) {
			assertThat(expected.getMessage(),
					CoreMatchers.containsString(MESSAGE_DIFFERENT_THE_CENTER_AND_NOT_EXIST_IN_TRASNPORTATION));
		}
	}

}
