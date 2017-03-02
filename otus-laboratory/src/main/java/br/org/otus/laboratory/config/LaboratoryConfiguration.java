package br.org.otus.laboratory.config;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class LaboratoryConfiguration {

	@SerializedName("_id")
	private ObjectId id;
	private List<TubeDefinition> tubeDefinitions;
	private CodeDefinition codeDefinitions;
	private GroupDefinition groupDefinitions;

	public LaboratoryConfiguration(List<TubeDefinition> tubeDefinitions, CodeDefinition codeDefinitions,
	    GroupDefinition groupDefinitions) {
		this.tubeDefinitions = tubeDefinitions;
		this.codeDefinitions = codeDefinitions;
		this.groupDefinitions = groupDefinitions;
	}

	public ObjectId getId() {
		return id;
	}

	public List<TubeDefinition> getTubeDefinitions() {
		return tubeDefinitions;
	}

	public CodeDefinition getCodeDefinitions() {
		return codeDefinitions;
	}

	public GroupDefinition getGroupDefinitions() {
		return groupDefinitions;
	}

	public static String serialize(LaboratoryConfiguration laboratory) {
		Gson builder = LaboratoryConfiguration.getGsonBuilder();
		return builder.toJson(laboratory);
	}

	public static LaboratoryConfiguration deserialize(String laboratoryJson) {
		Gson builder = LaboratoryConfiguration.getGsonBuilder();
		return builder.fromJson(laboratoryJson, LaboratoryConfiguration.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		return builder.create();
	}

}
