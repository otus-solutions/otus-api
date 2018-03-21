package org.ccem.otus.model.dataSources.examResult;

import java.time.LocalDateTime;
import java.util.List;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.ExamResult;

public class ExamResultDataSourceResult {

	private List<ExamResult> examResults;

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

	public List<ExamResult> getExamResults() {
		return examResults;
	}

}
