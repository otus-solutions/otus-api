package br.org.otus.laboratory.collect.aliquot;

public class Aliquot {

	private String objectType;
	private String code;
	private String name;
	private String container;
	private String role;
	private AliquotCollectionData aliquotCollectionData;

	public Aliquot(String objectType, String code, String name, String container, String role, AliquotCollectionData aliquotCollectionData) {
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

	public String getName() {
		return name;
	}

	public String getContainer() {
		return container;
	}

	public String getRole() {
		return role;
	}

	public AliquotCollectionData getAliquotCollectionData() {
		return aliquotCollectionData;
	}

}
