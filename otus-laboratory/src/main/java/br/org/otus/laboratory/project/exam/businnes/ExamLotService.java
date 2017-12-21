package br.org.otus.laboratory.project.exam.businnes;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.ExamLot;

public interface ExamLotService {

	ExamLot create(ExamLot examLot, String email) throws ValidationException, DataNotFoundException;

	ExamLot update(ExamLot examLot) throws DataNotFoundException, ValidationException;

	List<ExamLot> list();

	void delete(String id) throws DataNotFoundException;

	List<WorkAliquot> getAliquots() throws DataNotFoundException;

}
