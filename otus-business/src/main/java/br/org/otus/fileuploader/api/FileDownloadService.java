package br.org.otus.fileuploader.api;

import br.org.mongodb.gridfs.FileStoreBucket;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.service.download.FileDownload;
import org.ccem.otus.service.download.ZipFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FileDownloadService {

  @Inject
  private FileStoreBucket fileStoreBucket;

  public ZipFactory.Zip downloadFiles(ArrayList<String> oids) throws DataNotFoundException, IOException, ValidationException {

    List<ObjectId> objectIds = objectIdConverter(oids);


    List<FileDownload> files = fileStoreBucket.fetchFiles(objectIds);
    return ZipFactory.create(files);

  }

  private List<ObjectId> objectIdConverter(List<String> oids) throws ValidationException {

    ArrayList<String> errors = new ArrayList<>();

    List<ObjectId> objectIds = oids.stream().map(s -> {
      try {
        return new ObjectId(s);
      } catch (IllegalArgumentException e) {
        errors.add(s);
        return null;
      }
    }).collect(Collectors.toList());

    if (errors.size() > 0) {
      throw new ValidationException(new Throwable("Invalid Id"), errors);
    }

    return objectIds;
  }

}
