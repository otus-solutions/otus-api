package org.ccem.otus.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReportServiceBean implements ReportService {

	@Inject
	private ReportDao reportDao;

	@Inject
	private ParticipantDataSourceDao participantDataSourceDao;

	@Inject
	private ActivityDataSourceDao activityDataSourceDao;

	@Inject
	private ParticipantService participantService;

	@Override
	public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException {
		ObjectId reportObjectId = new ObjectId(reportId);
		ReportTemplate report = reportDao.findReport(reportObjectId);
		for (ReportDataSource dataSource : report.getDataSources()) {
			if (dataSource instanceof ParticipantDataSource) {
				((ParticipantDataSource) dataSource).getResult()
						.add(participantDataSourceDao.getResult(recruitmentNumber, (ParticipantDataSource) dataSource));
			} else if (dataSource instanceof ActivityDataSource) {
				((ActivityDataSource) dataSource).getResult()
						.add(activityDataSourceDao.getResult(recruitmentNumber, (ActivityDataSource) dataSource));
			}
		}
		return report;
	}

	@Override
	public List<ReportTemplate> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException {
		Participant participant = participantService.getByRecruitmentNumber(recruitmentNumber);
		return reportDao.getByCenter(participant.getFieldCenter().getAcronym());
	}

	@Override
	public String create(ReportTemplate reportTemplate, String userEmail)
			throws DataNotFoundException, ValidationException {
		ObjectId reportId = reportDao.insert(reportTemplate);
		return reportId.toString();
	}

	@Override
	public void delete(String id) throws DataNotFoundException {
		reportDao.deleteById(id);
	}

	@Override
	public List<ReportTemplate> list() {
		return reportDao.getAll();
	}

	@Override
	public ReportTemplate getByID(String id) throws DataNotFoundException {
		return reportDao.getById(id);
	}

	@Override
	public ReportTemplate update(ReportTemplate reportTemplate) throws DataNotFoundException, ValidationException {
		ReportTemplate updateResult = reportDao.update(reportTemplate);
		return updateResult;
	}

}
