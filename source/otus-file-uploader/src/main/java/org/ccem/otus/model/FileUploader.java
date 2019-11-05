package org.ccem.otus.model;

public class FileUploader {

	private String oid;
	private byte[] file;

	public FileUploader(byte[] file) {
		this.file = file;
	}

	public FileUploader(String oid, byte[] file) {
		this.oid = oid;
		this.file = file;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
}
