package br.org.otus.laboratory.project.aliquot;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.AliquotCollectionData;
import com.google.gson.Gson;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.util.List;

public class WorkAliquot extends Aliquot {

	private static String objectType = "WorkAliquot";
	private Long recruitmentNumber;
	private ImmutableDate birthdate;
	private Sex sex;
	private FieldCenter fieldCenter;

	public WorkAliquot(String code, String name, AliquotContainer container, AliquotRole role, AliquotCollectionData aliquotCollectionData, FieldCenter aliquotCenter) {
		super(objectType, code, name, container, role, aliquotCollectionData);
	}

	public WorkAliquot(Aliquot aliquot, Long recruitmentNumber, ImmutableDate birthdate, Sex sex, FieldCenter aliquotCenter) {
		super(objectType, aliquot.getCode(), aliquot.getName(), aliquot.getContainer(), aliquot.getRole(), aliquot.getAliquotCollectionData());
		this.setRecruitmentNumber(recruitmentNumber);
		this.setBirthdate(birthdate);
		this.setSex(sex);
		this.setFieldCenter(aliquotCenter);
	}

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

	public static String serialize(List<Aliquot> aliquots) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(aliquots);
	}

	public FieldCenter getFieldCenter() {
		return fieldCenter;
	}

	public void setFieldCenter(FieldCenter fieldCenter) {
		this.fieldCenter = fieldCenter;
	}

}
