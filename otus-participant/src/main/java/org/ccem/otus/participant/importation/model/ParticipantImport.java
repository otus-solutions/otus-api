package org.ccem.otus.participant.importation.model;

public class ParticipantImport {

  private Long rn;
  private String name;
  private String sex;
  private String birthdate;
  private String center;
  private Boolean late;

  public Long getRn() {
    return rn;
  }

  public String getName() {
    return name;
  }

  public String getCenter() {
    return center;
  }

  public String getSex() {
    return sex;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public Boolean getLate() {
    return late;
  }

  public void setLate(Boolean late) {
    this.late = late;
  }

  public boolean containsNullAttribute() {
    if (rn == null || name == null || birthdate == null || center == null || late == null)
      return true;
    else
      return false;
  }

  @Override
  public String toString() {
    return "ParticipantImport [rn=" + rn + ", name=" + name + ", sex=" + sex + ", birthdate=" + birthdate + ", center=" + center + ", late=" + late + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rn == null) ? 0 : rn.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ParticipantImport other = (ParticipantImport) obj;
    if (rn == null) {
      if (other.rn != null)
        return false;
    } else if (!rn.equals(other.rn))
      return false;
    return true;
  }

}
