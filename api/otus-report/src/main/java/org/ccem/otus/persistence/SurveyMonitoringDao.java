package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;

import java.util.ArrayList;

public interface SurveyMonitoringDao {
  ArrayList<ParticipantActivityReportDto> getParticipantActivities(Long rn);
}
