package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.participant.aliquot.Aliquot;

import java.util.ArrayList;
import java.util.List;

public class Tube implements Comparable<Tube> {

	private String objectType;
	private String type;
	private String moment;
	private String code;
	private String groupName;
	private List<Aliquot> aliquotes;
	private Integer order;
	private TubeCollectionData tubeCollectionData;

	public Tube(String type, String moment, String code, String groupName) {
		this.objectType = "Tube";
		this.type = type;
		this.moment = moment;
		this.code = code;
		this.groupName = groupName;
		this.aliquotes = new ArrayList<Aliquot>();
		this.tubeCollectionData = new TubeCollectionData();
	}

	public void addAllAliquotsThatNotContainsInList(List<Aliquot> aliquots) {
		for (Aliquot aliquot : aliquots) {
			if (!aliquotes.contains(aliquot))
				aliquotes.add(aliquot);
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

	public List<Aliquot> getAliquots() {
		return aliquotes;
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

}
