package br.org.otus.laboratory.participant.aliquot;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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
		GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
		return builder.create().toJson(aliquots);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Aliquot aliquot = (Aliquot) o;

		return code.equals(aliquot.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}
}
