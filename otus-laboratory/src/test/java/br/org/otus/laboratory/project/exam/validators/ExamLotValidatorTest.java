package br.org.otus.laboratory.project.exam.validators;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@RunWith(MockitoJUnitRunner.class)
public class ExamLotValidatorTest {

	@InjectMocks
	private ExamLotValidator examLotValidator;

	@Mock
	private ExamLot examLot;
	@Mock
	private ExamLotDao examLotDao;
	@Mock
	private TransportationLotDao transportationLotDao;
	@Mock
	private WorkAliquot aliquot;
	@Mock
	private FieldCenter fieldCenter;

	private List<WorkAliquot> aliquotList;

	@Before
	public void setup() {
		aliquotList = new ArrayList<WorkAliquot>();
		buildAliquotsInList();
		examLotValidator = new ExamLotValidator(examLotDao, transportationLotDao, examLot);
	}

	@Ignore
	@Test
	public void should_no_return_exception_when_aliquot_has_no_problems() {

	}

	@Ignore
	@Test(expected = ValidationException.class)
	public void should_return_exception_when_aliquot_exists_in_another_lot() throws ValidationException {
		Mockito.when(aliquot.getFieldCenter()).thenReturn(fieldCenter);

		//examLotValidator.validate();
	}

	private void buildAliquotsInList() {
		aliquotList.add(aliquot);
	}

}
