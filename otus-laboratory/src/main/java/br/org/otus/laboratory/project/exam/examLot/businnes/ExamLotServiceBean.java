package br.org.otus.laboratory.project.exam.examLot.businnes;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotAliquotFilterDTO;
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
		ExamLot diffExamLot = examLotDao.findByCode(examLot.getCode());

		List<Aliquot> newAliquots = new ArrayList<>();

		for (Aliquot newAliquot : examLot.getAliquotList())
		{
			boolean isNewAliquot = true;
			for (Aliquot oldAliquot : diffExamLot.getAliquotList()) {
				if (oldAliquot.getCode().equals(newAliquot.getCode())) {
					isNewAliquot = false;
				}
			}
			if (isNewAliquot){
				newAliquots.add(newAliquot);
			}
		}

		diffExamLot.setAliquotList(newAliquots);

		validateLot(diffExamLot);

		ArrayList<String> aliquotCodeList = examLot.getAliquotCodeList();
		aliquotDao.updateExamLotId(aliquotCodeList, diffExamLot.getLotId());

		return examLot;
	}

	@Override
	public List<ExamLot> list(String centerAcronym) {
		return examLotDao.find(centerAcronym);
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

	@Override
	public Aliquot validateNewAliquot(ExamLotAliquotFilterDTO examLotAliquotFilterDTO) throws DataNotFoundException, ValidationException {
		Aliquot aliquotToValidate = aliquotDao.find(examLotAliquotFilterDTO.getAliquotCode());
		if (!validateAliquotType(aliquotToValidate.getName(),examLotAliquotFilterDTO.getLotType())){
			throw new ValidationException(new Throwable("Invalid aliquot type."),aliquotToValidate.getName());
		} else if (!validateAliquotLocation(aliquotToValidate,examLotAliquotFilterDTO.getFieldCenter().getAcronym())){
			throw new ValidationException(new Throwable("Invalid center."),aliquotToValidate.getFieldCenter().getAcronym());
		} else if (aliquotToValidate.getExamLotId()!= null){
			String code = examLotDao.find(aliquotToValidate.getExamLotId()).getCode();
			throw new ValidationException(new Throwable("Already in a lot."),code);
		}
		return aliquotToValidate;
	}

	private boolean validateAliquotLocation(Aliquot aliquot,String lotCenter){
		return (aliquot.getFieldCenter().getAcronym().equals(lotCenter) || (aliquot.getTransportationLotId() != null));
	}

	private boolean validateAliquotType(String aliquotName,String lotType){
		return (aliquotName.equals(lotType));
	}

	private void validateLot(ExamLot examLot) throws ValidationException {
		ExamLotValidator examLotValidator = new ExamLotValidator(examLot, aliquotDao);
		examLotValidator.validate();
	}

}
