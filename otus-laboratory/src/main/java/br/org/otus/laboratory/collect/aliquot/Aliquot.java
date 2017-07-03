package br.org.otus.laboratory.collect.aliquot;

import br.org.otus.laboratory.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.collect.aliquot.enums.AliquotRole;

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

}
