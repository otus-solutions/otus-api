package org.ccem.otus.importation.model;

public class ParticipantImport {
    private Long recruitmentNumber;
    private String name;
    private String fieldCenterInitials;

    public Long getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(Long recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldCenterInitials() {
        return fieldCenterInitials;
    }

    public void setFieldCenterInitials(String fieldCenterInitials) {
        this.fieldCenterInitials = fieldCenterInitials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipantImport that = (ParticipantImport) o;

        return recruitmentNumber != null ? recruitmentNumber.equals(that.recruitmentNumber) : that.recruitmentNumber == null;

    }

    @Override
    public int hashCode() {
        return recruitmentNumber != null ? recruitmentNumber.hashCode() : 0;
    }

	@Override
	public String toString() {
		return "ParticipantImport [recruitmentNumber=" + recruitmentNumber + ", name=" + name + ", fieldCenterInitials="
				+ fieldCenterInitials + "]";
	}
    
}
