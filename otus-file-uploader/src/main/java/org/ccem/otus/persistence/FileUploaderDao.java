package org.ccem.otus.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploader;

public interface FileUploaderDao {
	
	String save(FileUploader file); 
	
	FileUploader getById(String oid) throws DataNotFoundException;

	void delete(String oid) throws DataNotFoundException; 
	
	
}
