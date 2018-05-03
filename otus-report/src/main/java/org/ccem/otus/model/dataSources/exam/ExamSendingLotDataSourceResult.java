package org.ccem.otus.model.dataSources.exam;

import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.utils.ObjectIdAdapter;

public class ExamSendingLotDataSourceResult {
	private ObjectId _id;

	public static String serialize(ExamDataSourceResult examResultDataSourceResult) {
		return getGsonBuilder().create().toJson(examResultDataSourceResult);
	}

	public static ExamSendingLotDataSourceResult deserialize(String DataSource) {
		return ExamSendingLotDataSourceResult.getGsonBuilder().create().fromJson(DataSource, ExamSendingLotDataSourceResult.class);
	}

	private static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());

		return builder;
	}

	public ObjectId getObjectId() {
		return _id;
	}
}
