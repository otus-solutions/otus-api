package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TransportationLotFacade {

	@Inject
	private TransportationLotService transportationLotService;

	public TransportationLot create(TransportationLot transportationLot) {
		return transportationLotService.create(transportationLot);
	}

	public List<TransportationLot> getLots() {
		return transportationLotService.list();
	}

	public TransportationLot update(TransportationLot transportationLot) {
		try {
			return transportationLotService.update(transportationLot);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public void delete(String id) {
		try {
			transportationLotService.delete(id);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<TransportationAliquot> getAliquots() {
		try {
			return transportationLotService.getAliquots();
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
