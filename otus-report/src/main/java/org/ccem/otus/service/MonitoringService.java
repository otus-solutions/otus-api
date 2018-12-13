package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.*;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

import java.util.ArrayList;
import java.util.List;

public interface MonitoringService {

    List<MonitoringDataSourceResult> get(String acronym) throws ValidationException;

    List<MonitoringCenter> getMonitoringCenter() throws DataNotFoundException;

    ActivityProgressReportDto getActivitiesProgress();

    ActivityProgressReportDto getActivitiesProgress(String center);

    ArrayList<ParticipantActivityReportDto> getParticipantActivities(Long rn);

    void setActivityApplicability(ActivityInapplicability applicability) throws DataNotFoundException;

    void deleteActivityApplicability(Long rn, String acronym) throws DataNotFoundException;

    LaboratoryProgressDTO getOrphanExams() throws DataNotFoundException;

    LaboratoryProgressDTO getQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataOfResultsByExam(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws DataNotFoundException;

    LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException;
}
