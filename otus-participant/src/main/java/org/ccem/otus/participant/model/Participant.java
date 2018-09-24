package org.ccem.otus.participant.model;

import java.time.LocalDateTime;


import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;


import com.google.gson.GsonBuilder;

public class Participant {

    private Long recruitmentNumber;
    private String name;
    private Sex sex;
    private ImmutableDate birthdate;
    private FieldCenter fieldCenter;
    private Boolean late;
    

	public Participant(Long recruitmentNumber) {
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

	public ImmutableDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ImmutableDate birthdate) {
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

    public Boolean getLate() {
      return late;
    }

    public void setLate(Boolean late) {
      this.late = late;
    }
    
    public static String serialize(Participant participantJson) {
      return Participant.getGsonBuilder().create().toJson(participantJson);
  }

  public static Participant deserialize(String participantJson) {
    Participant participant = Participant.getGsonBuilder().create().fromJson(participantJson, Participant.class);
      return participant;
  }
  
  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
}
   
}
