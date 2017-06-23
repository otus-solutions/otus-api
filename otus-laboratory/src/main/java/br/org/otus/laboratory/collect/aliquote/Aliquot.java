package br.org.otus.laboratory.collect.aliquote;

public class Aliquot {

	private String objectType;
	private String code;
	private String name;
	private String container;
	private String role;
	private CollectionData collectionData;

	public Aliquot(String objectType, String code, String name, String container, String role, CollectionData collectionData) {
		this.objectType = objectType;
		this.code = code;
		this.name = name;
		this.container = container;
		this.role = role;
		this.collectionData = collectionData;
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

	public CollectionData getCollectionData() {
		return collectionData;
	}

}
