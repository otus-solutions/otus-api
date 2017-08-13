package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TransportationLotFacade {

	@Inject
	private TransportationLotService transportationLotService;

	public TransportationLot create(TransportationLot transportationLot) {
		return transportationLotService.create(transportationLot);
	}

	public TransportationLot update(TransportationLot transportationLot) {
		return transportationLotService.update(transportationLot);
	}

	public ArrayList<TransportationLot> getLots() {
		return transportationLotService.list();
	}

	public void delete(String id) {
		transportationLotService.delete(id);
	}

	public List<TransportationAliquot> getAliquots() {
		return transportationLotService.getAliquots();
	}

}
