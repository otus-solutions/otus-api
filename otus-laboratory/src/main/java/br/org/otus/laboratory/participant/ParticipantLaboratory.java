package br.org.otus.laboratory.participant;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.collect.tube.Tube;
import br.org.otus.laboratory.exam.Exam;

public class ParticipantLaboratory {

	private Long recruitmentNumber;
	private String collectGroupName;
	private List<Tube> tubes;
	private List<Exam> exams;

	public ParticipantLaboratory(Long recruitmentNumber, String collectGroupName, List<Tube> tubes) {
		this.recruitmentNumber = recruitmentNumber;
		this.collectGroupName = collectGroupName;
		this.tubes = tubes;
		this.exams = new ArrayList<>();
	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public String getCollectGroupName() {
		return collectGroupName;
	}

	public List<Tube> getTubes() {
		return tubes;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public static String serialize(ParticipantLaboratory laboratory) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(laboratory);
	}

	public static ParticipantLaboratory deserialize(String laboratoryJson) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.fromJson(laboratoryJson, ParticipantLaboratory.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();

		return builder.create();
	}

}
