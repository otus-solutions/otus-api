package br.org.otus.laboratory.project.exam.examInapplicability.persistence;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface ExamInapplicabilityDao {

    void update(ExamInapplicability applicability) throws DataNotFoundException;

    void delete(ExamInapplicability applicability) throws DataNotFoundException;
}
