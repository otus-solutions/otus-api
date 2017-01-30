package org.ccem.otus.utils;

import java.lang.reflect.Type;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerAdapter implements JsonDeserializer<AnswerFill>, JsonSerializer<AnswerFill> {

	private static final String ANSWER_TYPE = "type";

	@Override
	public JsonElement serialize(AnswerFill src, Type typeOfSrc, JsonSerializationContext context) {
		return context.serialize(src);
	}

	@Override
	public AnswerFill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonPrimitive prim = (JsonPrimitive) json.getAsJsonObject().get(ANSWER_TYPE);
		String answertType = prim.getAsString();
		return context.deserialize(json, AnswerMapping.getEnumByObjectType(answertType).getAnswerClass());
	}

}