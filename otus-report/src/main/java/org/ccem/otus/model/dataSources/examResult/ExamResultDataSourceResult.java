package org.ccem.otus.model.dataSources.examResult;

import java.time.LocalDateTime;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;

public class ExamResultDataSourceResult {

	private String releaseDate;
	private String resultName;
	private String value;

	public static String serialize(ExamResultDataSourceResult examResultDataSourceResult) {
		return getGsonBuilder().create().toJson(examResultDataSourceResult);
	}

	public static ExamResultDataSourceResult deserialize(String DataSource) {
		GsonBuilder builder = ExamResultDataSourceResult.getGsonBuilder();
		return builder.create().fromJson(DataSource, ExamResultDataSourceResult.class);
	}

	private static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		return builder;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getValue() {
		return value;
	}

	public String getResultName() {
		return resultName;
	}

}
