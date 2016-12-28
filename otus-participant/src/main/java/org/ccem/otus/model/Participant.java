package org.ccem.otus.model;

import java.util.Date;

public class Participant {

    private Long recruitmentNumber;
    private String name;
    private Sex sex;
    private Date birthdate;
    private FieldCenter fieldCenter;

	public Participant(Long recruitmentNumber) {
		super();
		this.recruitmentNumber = recruitmentNumber;
	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldCenter getFieldCenter() {
		return fieldCenter;
	}

	public void setFieldCenter(FieldCenter fieldCenter) {
		this.fieldCenter = fieldCenter;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        return recruitmentNumber != null ? recruitmentNumber.equals(that.recruitmentNumber) : that.recruitmentNumber == null;
    }

    @Override
    public int hashCode() {
        return recruitmentNumber != null ? recruitmentNumber.hashCode() : 0;
    }
   
}
