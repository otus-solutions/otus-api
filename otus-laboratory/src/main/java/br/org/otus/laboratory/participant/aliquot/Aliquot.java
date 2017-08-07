package br.org.otus.laboratory.participant.aliquot;

import java.util.List;

import com.google.gson.Gson;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;

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

}
