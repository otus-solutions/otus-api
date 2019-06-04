package org.ccem.otus.persistence;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

import java.util.List;

public interface ActivityInapplicabilityDao {

  void update(ActivityInapplicability applicability) throws DataNotFoundException;

  void delete(Long rn, String acronym) throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);

}
