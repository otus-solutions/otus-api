package br.org.mongodb.gridfs;

import static com.mongodb.client.model.Filters.eq;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

public class FileStoreBucket {

	private static final String FILESTORE = "filestore";
	private static final int CHUNK_SIZE_BYTES = 3000000;

	@Inject
	protected MongoDatabase db;
	private GridFSBucket fileStore;
	private GridFSUploadOptions gridFSUploadOptions;

	public FileStoreBucket() {
		gridFSUploadOptions = new GridFSUploadOptions();
		gridFSUploadOptions.chunkSizeBytes(CHUNK_SIZE_BYTES);
	}

	@PostConstruct
	public void setUp() {
		fileStore = GridFSBuckets.create(db, FILESTORE);
	}

	public String store(FileUploaderPOJO form) {
		buildMetadata(form);
		ObjectId oid = fileStore.uploadFromStream(form.getName(), form.getFile(), gridFSUploadOptions);
		return oid.toString();
	}

	public Document findMetadata(String oid) {
		GridFSFile first = fileStore.find(eq("_id", new ObjectId(oid))).first();
		if(first == null) {
			return null;
		} else {
			return first.getMetadata();
		}
	}

	public InputStream download(String oid) throws DataNotFoundException {
		GridFSDownloadStream stream = fileStore.openDownloadStream(new ObjectId(oid));
		if (stream == null) {
			throw new DataNotFoundException(new Throwable("File with id " + oid + " not found"));
		}
		return stream;
	}

	public void delete(String oid) throws DataNotFoundException {
		if(findMetadata(oid) == null) {
			throw new DataNotFoundException(new Throwable("File with id " + oid + " not found"));
		} else {
			fileStore.delete(new ObjectId(oid));
		}
	}

	private void buildMetadata(FileUploaderPOJO form) {
		gridFSUploadOptions.metadata(new Document("item_id", form.getItemId())
				.append("recruitment_number", form.getRecruitmentNumber())
				.append("sent_date", form.getSentDate())
				.append("name", form.getName())
				.append("type", form.getType())
				.append("size", form.getSize())
				.append("interviewer", form.getInterviewer()));
	}

}
