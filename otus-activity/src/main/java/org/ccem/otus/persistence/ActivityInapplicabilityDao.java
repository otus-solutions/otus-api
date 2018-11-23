package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

public interface ActivityInapplicabilityDao {

  void update(ActivityInapplicability applicability) throws DataNotFoundException;

  void delete(Long rn, String acronym) throws DataNotFoundException;
}
