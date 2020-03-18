package org.ccem.otus.persistence;

import org.ccem.otus.model.survey.activity.OfflineActivityCollection;

public interface OfflineActivityDao {
  void persist(OfflineActivityCollection offlineActivityCollection);
}
