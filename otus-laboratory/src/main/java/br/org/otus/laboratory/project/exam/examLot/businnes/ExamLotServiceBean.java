package br.org.otus.laboratory.project.exam.examLot.businnes;

import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examLot.validators.ExamLotValidator;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Stateless
public class ExamLotServiceBean implements ExamLotService {

	@Inject
	private ExamLotDao examLotDao;
	@Inject
	private TransportationLotDao transportationLotDao;
	@Inject
	private AliquotDao aliquotDao;

	@Override
	public ExamLot create(ExamLot examLot, String email) throws ValidationException, DataNotFoundException {
		validateLot(examLot);
		examLot.setOperator(email);
		ObjectId examLotId = examLotDao.persist(examLot);
		aliquotDao.updateExamLotId(examLot.getAliquotCodeList(), examLotId);
		return examLot;
	}

	@Override
	public ExamLot update(ExamLot examLot) throws DataNotFoundException, ValidationException {
		validateLot(examLot);
		ExamLot oldExamLot = examLotDao.findByCode(examLot.getCode());

		ArrayList<String> aliquotCodeList = examLot.getAliquotCodeList();
		aliquotDao.updateExamLotId(aliquotCodeList, oldExamLot.getLotId());

		return examLot;
	}

	@Override
	public List<ExamLot> list() {
		return examLotDao.find();
	}

	@Override
	public void delete(String code) throws DataNotFoundException {
		ExamLot examLot = examLotDao.findByCode(code);
		aliquotDao.updateExamLotId(new ArrayList<>(), examLot.getLotId());
		examLotDao.delete(examLot.getLotId());
	}

	@Override
	public HashSet<Document> getAliquotsInfosInTransportationLots() throws DataNotFoundException {
		return transportationLotDao.getAliquotsInfoInTransportationLots();
	}

	private void validateLot(ExamLot examLot) throws ValidationException {
		ExamLotValidator examLotValidator = new ExamLotValidator(examLotDao, transportationLotDao, examLot, aliquotDao);
		examLotValidator.validate();
	}

}
