package br.org.otus.laboratory.project.transportation;

import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.collect.aliquot.Aliquot;

public class TransportationLot {

	private String objectType;
	private String code;
	private List<Aliquot> aliquotList;
	private LocalDateTime shipmentDate;
	private LocalDateTime processingDate;
	private String operator;
	
	public String getObjectType() {
		return objectType;
	}

	public String getCode() {
		return code;
	}

	public List<Aliquot> getAliquotList() {
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

	public static String serialize(TransportationLot transportationLot) {
		Gson builder = ParticipantLaboratory.getGsonBuilder();
		return builder.toJson(transportationLot);
	}

	public static TransportationLot deserialize(String transportationLot) {
		return ParticipantLaboratory.getGsonBuilder().fromJson(transportationLot, TransportationLot.class);
	}

}
