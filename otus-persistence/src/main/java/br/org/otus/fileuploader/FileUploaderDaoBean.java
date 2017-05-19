package br.org.otus.fileuploader;

import static com.mongodb.client.model.Filters.eq;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploader;
import org.ccem.otus.persistence.FileUploaderDao;

import com.mongodb.client.result.DeleteResult;

import br.org.mongodb.MongoGenericDao;

public class FileUploaderDaoBean extends MongoGenericDao<FileUploader> implements FileUploaderDao {

	private static final String COLLECTION_NAME = "file_upload";

	public FileUploaderDaoBean() {
		super(COLLECTION_NAME, FileUploader.class);
	}

	@Override
	public String save(FileUploader file) {
		this.persist(file);
		return file.getOid();
	}

	@Override
	public FileUploader getById(String oid) throws DataNotFoundException {
		FileUploader result = this.collection.find(eq("_id", new ObjectId(oid))).first();
		if (result == null) {
			throw new DataNotFoundException(new Throwable("File with id " + oid + " not found"));

		}
		return result;
	}

	@Override
	public void delete(String oid) throws DataNotFoundException {
		DeleteResult deleteResult = this.collection.deleteOne(eq("_id", new ObjectId(oid)));
		if (deleteResult.getDeletedCount() == 0) {
			throw new DataNotFoundException(new Throwable("File with id " + oid + " not found"));

		}

	}

}
