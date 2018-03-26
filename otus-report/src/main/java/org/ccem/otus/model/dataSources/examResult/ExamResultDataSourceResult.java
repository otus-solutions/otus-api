package org.ccem.otus.model.dataSources.examResult;

import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.utils.ObjectIdAdapter;

public class ExamResultDataSourceResult {

	private Exam exam;

	public static String serialize(ExamResultDataSourceResult examResultDataSourceResult) {
		return getGsonBuilder().create().toJson(examResultDataSourceResult);
	}

	public static ExamResultDataSourceResult deserialize(String DataSource) {
		return ExamResultDataSourceResult.getGsonBuilder().create().fromJson(DataSource, ExamResultDataSourceResult.class);
	}

	private static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());

		return builder;
	}

	public Exam getExam() {
		return exam;
	}
}
