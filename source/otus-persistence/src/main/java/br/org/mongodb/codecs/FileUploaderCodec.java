package br.org.mongodb.codecs;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FileUploader;

public class FileUploaderCodec implements Codec<FileUploader> {

  @Override
  public void encode(BsonWriter writer, FileUploader value, EncoderContext encoderContext) {
    writer.writeStartDocument();
    ObjectId objectId = new ObjectId();
    value.setOid(objectId.toString());

    writer.writeObjectId("_id", objectId);
    writer.writeBinaryData("file", new BsonBinary(value.getFile()));

    writer.writeEndDocument();
  }

  @Override
  public FileUploader decode(BsonReader reader, DecoderContext decoderContext) {
    reader.readStartDocument();
    String oid = reader.readObjectId().toString();
    byte[] file = reader.readBinaryData("file").getData();
    reader.readEndDocument();

    return new FileUploader(oid, file);
  }

  @Override
  public Class<FileUploader> getEncoderClass() {
    return FileUploader.class;
  }

}
