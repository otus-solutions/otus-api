package org.ccem.otus.persistence.laboratory;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;

public interface LaboratoryProgressDao {

    LaboratoryProgressDTO getOrphanExams() throws DataNotFoundException;

    LaboratoryProgressDTO getQuantitativeByTypeOfAliquots() throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfPendingResultsByAliquot() throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfStorageByAliquot() throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfResultsByExam() throws DataNotFoundException;
}
