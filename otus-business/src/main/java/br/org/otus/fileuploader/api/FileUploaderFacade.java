package br.org.otus.fileuploader.api;

import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class FileUploaderFacade {

	@Inject
	private FileStoreBucket fileStoreBucket;

	public InputStream getById(String oid) {
		try {
			return fileStoreBucket.download(oid);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public void delete(String oid) {
		try {
			fileStoreBucket.delete(oid);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public String upload(FileUploaderPOJO form) {
		return fileStoreBucket.store(form);
	}

	public InputStream list(ArrayList<String> oids) {
		try {

			return fileStoreBucket.downloadMultiple(oids);

		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
