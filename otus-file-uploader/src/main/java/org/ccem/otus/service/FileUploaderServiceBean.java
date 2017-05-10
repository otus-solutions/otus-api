package org.ccem.otus.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploader;
import org.ccem.otus.persistence.FileUploaderDao;

@Stateless
public class FileUploaderServiceBean implements FileUploaderService {

	@Inject
	private FileUploaderDao dao;

	@Override
	public String upload(FileUploader file) {
		return dao.save(file);
	}

	@Override
	public byte[] getById(String oid) throws DataNotFoundException {
		return dao.getById(oid).getFile();
	}

	@Override
	public void delete(String oid) throws DataNotFoundException {
		dao.delete(oid);
	}

}
