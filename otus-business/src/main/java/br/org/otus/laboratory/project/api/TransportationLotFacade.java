package br.org.otus.laboratory.project.api;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class TransportationLotFacade {

	@Inject
	private TransportationLotService transportationLotService;

	public TransportationLot create(TransportationLot transportationLot, String email) {
		try {
			return transportationLotService.create(transportationLot, email);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new HttpResponseException(
					ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(
					ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		}
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
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new HttpResponseException(
					ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
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

	public List<WorkAliquot> getAliquots() {
		try {
			return transportationLotService.getAliquots();
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<WorkAliquot> getAliquotsByPeriod(WorkAliquotFiltersDTO workAliquotFiltersDTO) {
		try {
			return transportationLotService.getAliquotsByPeriod(workAliquotFiltersDTO);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public WorkAliquot getAliquot(WorkAliquotFiltersDTO workAliquotFiltersDTO) {

		try {
			return transportationLotService.getAliquot(workAliquotFiltersDTO);
		} catch (ValidationException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(),e.getData()));
		}
	}

	

	
}
