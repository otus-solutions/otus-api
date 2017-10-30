package br.org.otus.laboratory.project.transportation;

import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDateTime;
import java.util.List;

public class TransportationLot {

	private String objectType;
	private String code;
	private List<TransportationAliquot> aliquotList;
	private LocalDateTime shipmentDate;
	private LocalDateTime processingDate;
	private String operator;
	private FieldCenter fieldCenter;

	public TransportationLot() {
		objectType = "TransportationLot";
	}

	public String getObjectType() {
		return objectType;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public List<TransportationAliquot> getAliquotList() {
		return aliquotList;
	}

	public LocalDateTime getShipmentDate() {
		return shipmentDate;
	}

	public LocalDateTime getProcessingDate() {
		return processingDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static String serialize(TransportationLot transportationLot) {
		Gson builder = getGsonBuilder().create();
		return builder.toJson(transportationLot);
	}

	public static TransportationLot deserialize(String transportationLot) {
		return getGsonBuilder().create().fromJson(transportationLot, TransportationLot.class);
	}

	public static GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		builder.serializeNulls();

		return builder;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TransportationLot that = (TransportationLot) o;

		return code.equals(that.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

	public FieldCenter getCenter() {
		return fieldCenter;
	}

	public void setCenter(FieldCenter center) {
		this.fieldCenter = center;
	}
}
