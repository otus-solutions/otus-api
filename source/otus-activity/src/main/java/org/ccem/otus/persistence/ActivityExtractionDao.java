package org.ccem.otus.persistence;

import java.util.HashMap;

public interface ActivityExtractionDao {
  HashMap<Long, String> getParticipantFieldCenter(String acronym, Integer version);
}
