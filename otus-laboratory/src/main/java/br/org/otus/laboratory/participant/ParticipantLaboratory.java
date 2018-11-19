package br.org.otus.laboratory.participant;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.exam.Exam;
import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParticipantLaboratory {

	private String objectType;
	private Long recruitmentNumber;
	private String collectGroupName;
	private List<Tube> tubes;
	private List<Exam> exams;

	public ParticipantLaboratory(Long recruitmentNumber, String collectGroupName, List<Tube> tubes) {
		this.objectType = "ParticipantLaboratory";
		this.recruitmentNumber = recruitmentNumber;
		this.collectGroupName = collectGroupName;
		this.tubes = tubes;
		this.exams = new ArrayList<>();
	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public String getObjectType() {
		return objectType;
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

	public List<Aliquot> getAliquotsList() {
		ArrayList<Aliquot> aliquotsList = new ArrayList<Aliquot>();
		for (Tube tube : tubes) {
			aliquotsList.addAll(tube.getAliquots());
		}

		return aliquotsList;
	}

	public static String serialize(ParticipantLaboratory laboratory) {
		GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
		return builder.create().toJson(laboratory);
	}

	public static ParticipantLaboratory deserialize(String laboratoryJson) {
		GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
		builder.registerTypeAdapter(Long.class, new LongAdapter());
		return builder.create().fromJson(laboratoryJson, ParticipantLaboratory.class);
	}

	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder;
	}

}
