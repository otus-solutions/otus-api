package br.org.otus.laboratory.participant.aliquot;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class SimpleAliquot {

	private String objectType;
	private String code;
	private String name;
	private AliquotContainer container;
	private AliquotRole role;
	private AliquotCollectionData aliquotCollectionData;
	private List<AliquotEvent> aliquotHistory;


	public SimpleAliquot(String objectType, String code, String name, AliquotContainer container, AliquotRole role, AliquotCollectionData aliquotCollectionData) {
    this.objectType = objectType;
    this.code = code;
    this.name = name;
    this.container = container;
    this.role = role;
    this.aliquotCollectionData = aliquotCollectionData;
  }

  public SimpleAliquot() {}

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

	public List<AliquotEvent> getAliquotHistory() { return aliquotHistory; }

	public AliquotCollectionData getAliquotCollectionData() {
		return aliquotCollectionData;
	}

	public static String serialize(SimpleAliquot simpleAliquot) {
		Gson builder = SimpleAliquot.getGsonBuilder().create();
		return builder.toJson(simpleAliquot);
	}

	public static SimpleAliquot deserialize(String simpleAliquotJson) {
		Gson builder = SimpleAliquot.getGsonBuilder().create();
		return builder.fromJson(simpleAliquotJson, SimpleAliquot.class);
	}

	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();
		return builder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SimpleAliquot aliquot = (SimpleAliquot) obj;
		return code.equals(aliquot.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

	public void setContainer(AliquotContainer container) {
		this.container = container;
	}

	public void setRole(AliquotRole role) {
		this.role = role;
	}

	public void setAliquotCollectionData(AliquotCollectionData aliquotCollectionData) {
		this.aliquotCollectionData = aliquotCollectionData;
	}
}
