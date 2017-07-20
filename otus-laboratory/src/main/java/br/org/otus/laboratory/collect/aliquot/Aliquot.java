package br.org.otus.laboratory.collect.aliquot;

import java.time.LocalDateTime;
import java.util.List;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import br.org.otus.laboratory.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.ParticipantLaboratory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Aliquot {

	private String objectType;
	private String code;
	private String name;
	private AliquotContainer container;
	private AliquotRole role;
	private AliquotCollectionData aliquotCollectionData;

	public Aliquot(String objectType, String code, String name, AliquotContainer container, AliquotRole role, AliquotCollectionData aliquotCollectionData) {
		this.objectType = objectType;
		this.code = code;
		this.name = name;
		this.container = container;
		this.role = role;
		this.aliquotCollectionData = aliquotCollectionData;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getCode() {
		return code;
	}
	
	public AliquotContainer getContainer() {
		return container;
	}

	public AliquotRole getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public AliquotCollectionData getAliquotCollectionData() {
		return aliquotCollectionData;
	}
	
	public static String serialize(List<Aliquot> aliquots) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(aliquots);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder.create();
	}


}
