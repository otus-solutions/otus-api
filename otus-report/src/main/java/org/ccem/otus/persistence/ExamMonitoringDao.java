package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ParticipantExamReportDto;

import java.util.ArrayList;

public interface ExamMonitoringDao {
  ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn);
}
