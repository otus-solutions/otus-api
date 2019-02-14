package br.org.mongodb.gridfs;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FileUploaderPOJO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.mongodb.async.client.gridfs.helpers.AsynchronousChannelHelper.channelToOutputStream;
import static com.mongodb.client.model.Filters.eq;

public class FileStoreBucket {

	private static final String FILESTORE = "filestore";
	private static final int CHUNK_SIZE_BYTES = 3000000;

	@Inject
	protected MongoDatabase db;
	protected MongoCollection<Document> collection;
	private GridFSBucket fileStore;
	private GridFSUploadOptions gridFSUploadOptions;

	public FileStoreBucket() {
		gridFSUploadOptions = new GridFSUploadOptions();
		gridFSUploadOptions.chunkSizeBytes(CHUNK_SIZE_BYTES);
	}

	@PostConstruct
	public void setUp() {
		fileStore = GridFSBuckets.create(db, FILESTORE);
		collection = db.getCollection("filestore.files", Document.class);
	}

	public Document distinctIds(){
		return collection.aggregate(Arrays.asList(new Document("$group",new Document("_id","{}").append("ObjectIds",new Document("$push","$_id"))))).first();
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

	public List<FileDownload> downloadMultiple(List<ObjectId> objectIds) throws DataNotFoundException, InterruptedException, IOException {
		Document query = new Document(
				"_id", new Document(
						"$in", objectIds
		)
		);
    MongoCursor<GridFSFile> iterator = fileStore.find(query).iterator();



    List<FileDownload> fileList = new ArrayList<>();


    while (iterator.hasNext()) {
      GridFSFile fileMetadata = iterator.next();
      FileDownload fileDownload = new FileDownload(fileMetadata.getObjectId().toString(), fileMetadata.getFilename());
      fileList.add(fileDownload);
    }



    List<InputStream> lits = new ArrayList<>();

    objectIds.forEach(objectId -> {
      try {

        InputStream download = download(objectId.toString());
//        fileList
        
      } catch (DataNotFoundException e) {
        e.printStackTrace();
      }
    });

		return lits;
	}

}
