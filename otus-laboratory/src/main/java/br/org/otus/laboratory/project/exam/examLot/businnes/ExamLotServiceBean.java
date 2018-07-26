package br.org.otus.laboratory.project.exam.examLot.businnes;

import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examLot.validators.ExamLotValidator;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@Stateless
public class ExamLotServiceBean implements ExamLotService {

	@Inject
	private ExamLotDao examLotDao;
	@Inject
	private TransportationLotDao transportationLotDao;

	@Inject
	private LaboratoryConfigurationService laboratoryConfigurationService;

	@Override
	public ExamLot create(ExamLot examLot, String email) throws ValidationException, DataNotFoundException {
		validateLot(examLot);
		examLot.setOperator(email);
		examLotDao.persist(examLot);
		return examLot;
	}

	@Override
	public ExamLot update(ExamLot examLot) throws DataNotFoundException, ValidationException {
		validateLot(examLot);
		ExamLot updateResult = examLotDao.update(examLot);
		return updateResult;
	}

	@Override
	public List<ExamLot> list() {
		return examLotDao.find();
	}

	@Override
	public void delete(String id) throws DataNotFoundException {
		examLotDao.delete(id);
	}

	@Override
	public List<WorkAliquot> getAliquots() throws DataNotFoundException {
		return examLotDao.getAllAliquotsInDB();
	}

	@Override
	public HashSet<Document> getAliquotsInfosInTransportationLots() throws DataNotFoundException {
		return transportationLotDao.getAliquotsInfoInTransportationLots();
	}

	private void validateLot(ExamLot examLot) throws ValidationException {
		ExamLotValidator examLotValidator = new ExamLotValidator(examLotDao, transportationLotDao, examLot);
		examLotValidator.validate();
	}

}
