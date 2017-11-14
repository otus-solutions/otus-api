package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class TubeCollectionData {

	private String objectType;
	private boolean isCollected;
	private String metadata;
	private String operator;
	private LocalDateTime time;

	public TubeCollectionData() {
		this.objectType = "TubeCollectionData";
		this.isCollected = false;
		this.metadata = "";
		this.operator = "";
	}
	
	public String getObjectType() {
		return objectType;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public String getMetadata() {
		return metadata;
	}

	public String getOperatorEmail() {
		return operator;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public static String serialize(TubeCollectionData tubeCollectionData) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(tubeCollectionData);
	}

	public static Tube deserialize(String tubeJson) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.fromJson(tubeJson, Tube.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder.create();
	}
}
