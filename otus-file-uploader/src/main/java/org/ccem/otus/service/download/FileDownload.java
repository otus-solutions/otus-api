package org.ccem.otus.service.download;

import java.io.InputStream;
import java.util.Objects;

public class FileDownload {

  private String oid;
  private String name;
  private InputStream fileStream;

  public FileDownload(String oid, String name, InputStream fileStream) {
    this.oid = oid;
    this.name = name;
    this.fileStream = fileStream;
  }

  public String getOid() {
    return oid;
  }

  public String getName() {
    return name;
  }

  public InputStream getFileStream() {
    return fileStream;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileDownload that = (FileDownload) o;
    return oid.equals(that.oid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oid);
  }
}
