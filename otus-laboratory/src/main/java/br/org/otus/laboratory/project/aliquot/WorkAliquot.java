package br.org.otus.laboratory.project.aliquot;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.aliquot.AliquotCollectionData;

public class WorkAliquot extends SimpleAliquot {

	private static String objectType = "WorkAliquot";
	private String tubeCode;
	private ObjectId transportationLotId;
	private ObjectId examLotId;
	private Long recruitmentNumber;
	private Sex sex;
	private FieldCenter fieldCenter;
	private ImmutableDate birthdate;

  public WorkAliquot(String objectType, String code, String name, AliquotContainer container, AliquotRole role, AliquotCollectionData aliquotCollectionData) {
    super(objectType, code, name, container, role, aliquotCollectionData);
  }

//	public WorkAliquot(String code, String name, AliquotContainer container, AliquotRole role, AliquotCollectionData aliquotCollectionData, FieldCenter aliquotCenter) {
//
//	}
//
//	public WorkAliquot(SimpleAliquot aliquot, Long recruitmentNumber, ImmutableDate birthdate, Sex sex, FieldCenter aliquotCenter) {
//		this.setRecruitmentNumber(recruitmentNumber);
//		this.setBirthdate(birthdate);
//		this.setSex(sex);
//		this.setFieldCenter(aliquotCenter);
//	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public void setRecruitmentNumber(Long recruitmentNumber) {
		this.recruitmentNumber = recruitmentNumber;
	}

	public ImmutableDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ImmutableDate birthdate) {
		this.birthdate = birthdate;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public static String serialize(WorkAliquot workAliquot) {
		Gson builder = getGsonBuilder().create();
		return builder.toJson(workAliquot);
	}

	public FieldCenter getFieldCenter() {
		return fieldCenter;
	}

	public void setFieldCenter(FieldCenter fieldCenter) {
		this.fieldCenter = fieldCenter;
	}

	public static WorkAliquot deserialize(String workAliquot) {	
		return WorkAliquot.getGsonBuilder().create().fromJson(workAliquot, WorkAliquot.class);
	}

	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder;
	}


	public void setTransportationLotId(ObjectId transportationLotId) {
		this.transportationLotId = transportationLotId;
	}

	public void setExamLotId(ObjectId examLotId) {
		this.examLotId = examLotId;
	}
}
