package br.org.otus.laboratory.project.exam.businnes;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;

public class ExamLotServiceBean implements ExamLotService {

	@Inject
	private ExamLotDao examLotDao;

	@Override
	public ExamLot create(ExamLot examLot, String email) throws ValidationException, DataNotFoundException {
		examLot.setOperator(email);
		examLotDao.persist(examLot);
		return examLot;
	}

	@Override
	public ExamLot update(ExamLot examLot) throws DataNotFoundException, ValidationException {
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
		return examLotDao.getAliquots();
	}

}
