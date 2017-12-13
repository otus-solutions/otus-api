package br.org.otus.laboratory.project.exam;

import java.time.LocalDateTime;
import java.util.List;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;

public class ExamLot {

	private String objectType;
	private String aliquotName;
	private String code;
	private List<WorkAliquot> aliquotList;
	private LocalDateTime realizationDate;
	private String operator;
	private FieldCenter fieldCenter;

	public ExamLot() {
		objectType = "ExamLot";
	}

	public static ExamLot deserialize(String examLot) {
		return getGsonBuilder().create().fromJson(examLot, ExamLot.class);
	}

	public static String serialize(ExamLot examLot) {
		Gson builder = getGsonBuilder().create();
		return builder.toJson(examLot);
	}

	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder;
	}

	public String getObjectType() {
		return objectType;
	}

	public List<WorkAliquot> getAliquotList() {
		return aliquotList;
	}

	public void setAliquotList(List<WorkAliquot> aliquotList) {
		this.aliquotList = aliquotList;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public FieldCenter getFieldCenter() {
		return fieldCenter;
	}

	public void setFieldCenter(FieldCenter fieldCenter) {
		this.fieldCenter = fieldCenter;
	}

	public LocalDateTime getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(LocalDateTime realizationDate) {
		this.realizationDate = realizationDate;
	}

	public String getAliquotName() {
		return aliquotName;
	}

	public void setAliquotName(String aliquotName) {
		this.aliquotName = aliquotName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ExamLot that = (ExamLot) o;

		return code.equals(that.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}
}
