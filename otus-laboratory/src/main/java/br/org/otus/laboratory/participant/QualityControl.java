package br.org.otus.laboratory.participant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QualityControl {

	private Long recruitmentNumber;
	private String code;

	public QualityControl(Long recruitmentNumber, String code) {
		this.recruitmentNumber = recruitmentNumber;
		this.code = code;
	}

	public Long getRecruitmentNumber() {
		return recruitmentNumber;
	}

	public String getCode() {
		return code;
	}

	public static QualityControl deserialize(String qualityControlJson) {
		Gson builder = QualityControl.getGsonBuilder();
		return builder.fromJson(qualityControlJson, QualityControl.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();

		return builder.create();
	}
}