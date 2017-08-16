package br.org.otus.laboratory.project.transportation.validarors;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class TransportationLotValidator {

	private TransportationLotDao transportationLotDao;
	private TransportationLot transportationLot;
	private TransportationLotValidationResult transportationLotValidationResult;

	public TransportationLotValidator(TransportationLotDao transportationLotDao, TransportationLot transportationLot) {
		this.transportationLotDao = transportationLotDao;
		this.transportationLot = transportationLot;
		this.transportationLotValidationResult = new TransportationLotValidationResult();
	}

	public void validate() {

	}
}
