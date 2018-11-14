package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tube implements Comparable<Tube> {

	private String objectType;
	private String type;
	private String moment;
	private String code;
	private String groupName;
	private List<SimpleAliquot> aliquots;
	private Integer order;
	private TubeCollectionData tubeCollectionData;

	public Tube(String type, String moment, String code, String groupName) {
		this.objectType = "Tube";
		this.type = type;
		this.moment = moment;
		this.code = code;
		this.groupName = groupName;
		this.aliquots = new ArrayList<SimpleAliquot>();
		this.tubeCollectionData = new TubeCollectionData();
	}

	public void addAllAliquotsThatNotContainsInList(List<SimpleAliquot> aliquots) {
		for (SimpleAliquot aliquot : aliquots) {
			if (!aliquots.contains(aliquot))
				aliquots.add(aliquot);
		}
	}

	public String getType() {
		return type;
	}

	public String getMoment() {
		return moment;
	}

	public String getCode() {
		return code;
	}

	public String getGroupName() {
		return groupName;
	}

	public List<SimpleAliquot> getAliquots() {
		return aliquots;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean isOrdered() {
		if (this.order == null) {
			return false;
		} else {
			return true;
		}
	}

  public void addAliquot(Aliquot aliquot) {
	  if (this.aliquots == null ) {
	    this.aliquots = new ArrayList<>();
    }
    this.aliquots.add(aliquot.getSimpleAliquot());
  }

	public void setAliquots(List<SimpleAliquot> aliquots) {
    this.aliquots = aliquots;
  }

  public String getObjectType() {
		return objectType;
	}

	public TubeCollectionData getTubeCollectionData() {
		return tubeCollectionData;
	}

	@Override
	public int compareTo(Tube otherTube) {
		if (this.order == null && otherTube.getOrder() != null) {
			return 1;
		}

		if (this.order == null && otherTube.getOrder() == null) {
			return 0;
		}

		if (this.order != null && otherTube.getOrder() == null) {
			return -1;
		}

		if (this.order < otherTube.getOrder()) {
			return -1;
		} else {
			return 1;
		}
	}

	public static String serialize(Tube tube) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(tube);
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
