package br.org.otus.laboratory.project.transportation.aliquot;

import java.util.List;

import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.Gson;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.AliquotCollectionData;

public class TransportationAliquot extends Aliquot {
	
	private Long recruitmentNumber;
	private ImmutableDate birthdate;
	private Sex sex;
	
	public TransportationAliquot(String objectType, String code, String name, AliquotContainer container,
			AliquotRole role, AliquotCollectionData aliquotCollectionData) {
		super(objectType, code, name, container, role, aliquotCollectionData);
		// TODO Auto-generated constructor stub
	}
	
	public TransportationAliquot(Aliquot aliquot, Long recruitmentNumber, ImmutableDate birthdate, Sex sex) {
		super(aliquot.getObjectType(), aliquot.getCode(), aliquot.getName(), aliquot.getContainer(), aliquot.getRole(), aliquot.getAliquotCollectionData());
		this.setRecruitmentNumber(recruitmentNumber);
		this.setBirthdate(birthdate);
		this.setSex(sex);
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
}
