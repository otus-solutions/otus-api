package br.org.mongodb.gridfs;

import java.util.Objects;

public class FileDownload {

  private String oid;
  private String name;
  private byte[] file;

  public FileDownload(String oid, String name) {
    this.oid = oid;
    this.name = name;
  }

  public String getOid() {
    return oid;
  }

  public String getName() {
    return name;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
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
