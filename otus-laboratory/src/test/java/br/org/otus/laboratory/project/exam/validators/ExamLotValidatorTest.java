package br.org.otus.laboratory.project.exam.validators;

import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

	@InjectMocks
	private ExamLotValidator examLotValidator;
	@Mock
	private ExamLot examLot;
	@Mock
	private ExamLotDao examLotDao;
	@Mock
	private TransportationLotDao transportationLotDao;
	@Mock
	private WorkAliquot workAliquot;
	@Mock
	private FieldCenter fieldCenter;

	private ExamLotValidationResult examLotValidationResult = spy(new ExamLotValidationResult());
	private List<WorkAliquot> aliquotList = new ArrayList<>();
	private List<ExamLot> examLotList;

	@Before
	public void setUp() throws Exception {
		//aliquotList = new ArrayList<>();
		examLotList = new ArrayList<>();
	}

	@Test
	public void method_validate() throws ValidationException, DataNotFoundException {
		ExamLot examLotResult = Mockito.mock(ExamLot.class);

		when(examLotDao.find()).thenReturn(examLotList);
		when(examLotDao.getAliquots()).thenReturn(aliquotList);

		when(examLot.getAliquotList()).thenReturn(aliquotList);
		when(examLot.getAliquotName()).thenReturn(BIOCHEMICAL_SERUM);
		when(examLot.getFieldCenter()).thenReturn(fieldCenter);
		
		when(workAliquot.getName()).thenReturn(BIOCHEMICAL_SERUM);
		when(workAliquot.getFieldCenter()).thenReturn(fieldCenter);
		when(fieldCenter.getAcronym()).thenReturn(FIELD_CENTER_RS);
		
		aliquotList.add(workAliquot);
		examLotList.add(examLotResult);

		examLotValidator.validate();

	}

	// Whitebox.invokeMethod(examLotValidator, "checkIfAliquotsExist");

}
