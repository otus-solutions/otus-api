package org.ccem.otus.persistence.laboratory;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;

public interface LaboratoryProgressDao {

    LaboratoryProgressDTO getDataOrphanByExams() throws DataNotFoundException;

    LaboratoryProgressDTO getDataQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataByExam(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws  DataNotFoundException;

    LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException;
}
