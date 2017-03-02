package br.org.otus.laboratory.participant;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.config.CodeDefinition;
import br.org.otus.laboratory.config.LaboratoryConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LaboratoryParticipant {

	private Long recruitmentNumber;
	private String cqGroup;
	public List<Tube> tubes;
	public List<Exam> exams;

	public LaboratoryParticipant(Long recruitmentNumber, String cqGroup, List<Tube> tubes, List<Exam> exams) {
		this.recruitmentNumber = recruitmentNumber;
		this.cqGroup = cqGroup;
		this.tubes = new ArrayList<>();
		this.exams = new ArrayList<>();
	}

	public LaboratoryParticipant(Long recruitmentNumber, List<Tube> tubes, List<Exam> exams) {
		this.recruitmentNumber = recruitmentNumber;
		this.tubes = new ArrayList<>();
		this.exams = new ArrayList<>();
	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public List<Tube> getTubes() {
		return tubes;
	}

	public void setTubes(List<Tube> tubeList) {
		tubes.addAll(tubeList);
	}

	public static String serialize(LaboratoryParticipant laboratory) {
		Gson builder = LaboratoryParticipant.getGsonBuilder();
		return builder.toJson(laboratory);
	}

	public static LaboratoryParticipant deserialize(String laboratoryJson) {
		Gson builder = LaboratoryParticipant.getGsonBuilder();
		return builder.fromJson(laboratoryJson, LaboratoryParticipant.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();

		return builder.create();
	}
}
