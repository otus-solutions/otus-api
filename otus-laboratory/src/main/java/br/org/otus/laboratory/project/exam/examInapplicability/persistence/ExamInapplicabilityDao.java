package br.org.otus.laboratory.project.exam.examInapplicability.persistence;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamInapplicabilityDao {

    void update(ExamInapplicability applicability) throws DataNotFoundException;

    void delete(ExamInapplicability applicability) throws DataNotFoundException;

    AggregateIterable<Document> aggregate(List<Bson> query);
}
