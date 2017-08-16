package br.org.otus.laboratory.project.transportation.validarors;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

import java.util.List;

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
		checkForAliquotsOnAnotherLots();
		if (!transportationLotValidationResult.isValid()){
			//throw
		}
	}

	private void checkForAliquotsOnAnotherLots(){
		final List<TransportationLot>  transportationLotList = transportationLotDao.find();
		boolean duplicate = false;
		transportationLot.getAliquotList().forEach(transportationAliquot ->
				transportationLotList.forEach(transportationLot1 -> {
					boolean result = transportationLot1.getAliquotList().contains(transportationAliquot);
					if (result) {
						transportationLotValidationResult.setValid(false);
						transportationLotValidationResult.pushConflict(transportationAliquot);
					}
				}));
	}
}
