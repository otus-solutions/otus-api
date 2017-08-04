package br.org.otus.laboratory.participant.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;

public class UpdateAliquotsDTO {
	
	private long recruitmentNumber;
	private List<UpdateTubeAliquotsDTO> tubes;

	public List<UpdateTubeAliquotsDTO> getUpdateTubeAliquots() {
		return tubes;
	}
	
	public void setRecruitmentNumber(long recruitmentNumber) {
		this.recruitmentNumber = recruitmentNumber;
	}

	public static String serialize(UpdateAliquotsDTO updateAliquotsDTO) {
		return UpdateAliquotsDTO.getGsonBuilder().create().toJson(updateAliquotsDTO);
	}

	public long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public static UpdateAliquotsDTO deserialize(String updateAliquotsDTO) {
		return UpdateAliquotsDTO.getGsonBuilder().create().fromJson(updateAliquotsDTO, UpdateAliquotsDTO.class);
	}

	public static GsonBuilder getGsonBuilder() {
		return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
	}

}
