package org.ccem.otus.enums;

import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityReportAnswerFillingDataSource;
import org.ccem.otus.model.dataSources.activity.AnswerFillingDataSource;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;

public enum DataSourceMapping {
	PARTICIPANT_DATASOURCE(ParticipantDataSource.class, "Participant"), 
	ACTIVITY_DATASOURCE(ActivityDataSource.class, "Activity"), 
	EXAM_DATASOURCE(ExamDataSource.class, "Exam"),
	ANSWER_DATASOURCE(AnswerFillingDataSource.class,"AnswerFilling"),
	ACTIVITY_REPORT_ANSWER_DATASOURCE(ActivityReportAnswerFillingDataSource.class,"ActivityReportAnswerFilling");

	private Class<? extends ReportDataSource> dataSource;
	private String Key;

	DataSourceMapping(Class<? extends ReportDataSource> dataSource, String Key) {
		this.dataSource = dataSource;
		this.Key = Key;
	}

	public Class<? extends ReportDataSource> getItemClass() {
		return this.dataSource;
	}

	public String getDataSourceKey() {
		return this.Key;
	}

	public static DataSourceMapping getEnumByObjectType(String objectType) {
		DataSourceMapping aux = null;
		DataSourceMapping[] var2 = values();

		for (DataSourceMapping item : var2) {
			if (item.getDataSourceKey().equals(objectType)) {
				aux = item;
			}
		}

		if (aux == null) {
			throw new RuntimeException("Error: " + objectType + " was not found at DataSourceMapping.");
		} else {
			return aux;
		}
	}

}
