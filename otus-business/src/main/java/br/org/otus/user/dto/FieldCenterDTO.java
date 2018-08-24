package br.org.otus.user.dto;

public class FieldCenterDTO {

	public String acronym;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FieldCenterDTO that = (FieldCenterDTO) o;

    return acronym != null ? acronym.equals(that.acronym) : that.acronym == null;
  }

  @Override
  public int hashCode() {
    return acronym != null ? acronym.hashCode() : 0;
  }
}
