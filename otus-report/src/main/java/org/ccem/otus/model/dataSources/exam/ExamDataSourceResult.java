package org.ccem.otus.model.dataSources.exam;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.utils.ObjectIdAdapter;

public class ExamDataSourceResult {

    private ObjectId _id;
    private ObjectId examSendingLotId;
    private String objectType;
    private String name;
    private List<ExamResult> examResults;
    private List<Observation> observations;

	public static String serialize(ExamDataSourceResult examResultDataSourceResult) {
		return getGsonBuilder().create().toJson(examResultDataSourceResult);
	}

	public static ExamDataSourceResult deserialize(String DataSource) {
		return ExamDataSourceResult.getGsonBuilder().create().fromJson(DataSource, ExamDataSourceResult.class);
	}

	private static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());

		return builder;
	}
}
