package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;

import java.util.ArrayList;

public interface ExamMonitoringDao {
  ParticipantExamReportDto getParticipantExams(Long rn) throws DataNotFoundException;
}
