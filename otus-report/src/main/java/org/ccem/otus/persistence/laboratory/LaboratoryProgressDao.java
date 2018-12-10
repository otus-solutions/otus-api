package org.ccem.otus.persistence.laboratory;

import org.ccem.otus.model.monitoring.laboratory.OrphanExamsProgressDTO;

public interface LaboratoryProgressDao {

    OrphanExamsProgressDTO getOrphanExams();
}
