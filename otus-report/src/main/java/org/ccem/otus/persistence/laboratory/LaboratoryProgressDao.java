package org.ccem.otus.persistence.laboratory;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;

public interface LaboratoryProgressDao {

    LaboratoryProgressDTO getOrphanExams(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfResultsByExam(String center) throws DataNotFoundException;
}
