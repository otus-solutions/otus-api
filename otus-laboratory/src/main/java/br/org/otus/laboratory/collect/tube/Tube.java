package br.org.otus.laboratory.collect.tube;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.collect.aliquote.Aliquote;

public class Tube implements Comparable<Tube> {

	private String type;
	private String moment;
	private String code;
	private String groupName;
	private List<Aliquote> aliquotes;
	private Integer order;

	public Tube(String type, String moment, String code, String groupName) {
		this.type = type;
		this.moment = moment;
		this.code = code;
		this.groupName = groupName;
		this.aliquotes = new ArrayList<>();
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

	public List<Aliquote> getAliquotes() {
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
