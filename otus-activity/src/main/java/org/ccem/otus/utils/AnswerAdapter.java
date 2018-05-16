package org.ccem.otus.utils;

import java.lang.reflect.Type;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.IntegerAnswer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerAdapter implements JsonDeserializer<AnswerFill>, JsonSerializer<AnswerFill> {

	private static final String ANSWER_VALUE = "value";
	private static final String ANSWER_OBJECT_TYPE = "objectType";
	private static final String ANSWER_TYPE = "type";

	@Override
	public JsonElement serialize(AnswerFill src, Type typeOfSrc, JsonSerializationContext context) {
		return context.serialize(src);
	}

	@Override
	public AnswerFill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		JsonPrimitive type = (JsonPrimitive) json.getAsJsonObject().get(ANSWER_TYPE);
		String answertType = type.getAsString();

		if (AnswerMapping.isEquals(AnswerMapping.INTEGER_QUESTION, answertType)) {
			return this.deserializeIntegerAnswer(json);
		} else if (AnswerMapping.isEquals(AnswerMapping.GRID_INTEGER_QUESTION, answertType)) {
			// TODO:
		}

		return context.deserialize(json, AnswerMapping.getEnumByObjectType(answertType).getAnswerClass());
	}

	private IntegerAnswer deserializeIntegerAnswer(JsonElement json) {
		IntegerAnswer answer = new IntegerAnswer();

		JsonElement numberLong = json.getAsJsonObject().get(ANSWER_VALUE);

		if(json.getAsJsonObject().has("$numberLong")) {
			answer.setValue(numberLong.getAsJsonObject().get("$numberLong").getAsLong());
		} else {
			answer.setValue(numberLong.getAsLong());
		}

		answer.setObjectType(json.getAsJsonObject().get(ANSWER_OBJECT_TYPE).getAsString());
		answer.setType(json.getAsJsonObject().get(ANSWER_TYPE).getAsString());

		return answer;
	}

}