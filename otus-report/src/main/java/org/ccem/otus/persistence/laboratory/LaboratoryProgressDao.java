package org.ccem.otus.persistence.laboratory;

import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;

public interface LaboratoryProgressDao {

    LaboratoryProgressDTO getOrphanExams();

    LaboratoryProgressDTO getQuantitativeByTypeOfAliquots();

    LaboratoryProgressDTO getDataOfPendingResultsByAliquot();

    LaboratoryProgressDTO getDataOfStorageByAliquot();
}
