package org.ccem.otus.persistence;

import java.util.HashMap;
import java.util.Map;

public interface ActivityExtractionDao {
  Map<Long, String> getParticipantFieldCenter(String acronym, Integer version);
}
