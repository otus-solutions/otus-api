package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Stateless
public class TransportationLotServiceBean implements TransportationLotService {

	@Inject
	private TransportationLotDao transportationLotDao;

	@Override
	public TransportationLot create(TransportationLot transportationLot) {
		//generate code - UUID?
		String code = UUID.randomUUID().toString();
		transportationLot.setCode(code);
		transportationLotDao.persist(transportationLot);
		return transportationLot;
	}

	@Override
	public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException {
		TransportationLot updateResult = transportationLotDao.update(transportationLot);
		return updateResult;

	}

	@Override
	public List<TransportationLot> list() {
		return transportationLotDao.find();
	}

	@Override
	public void delete(String id) throws DataNotFoundException{
		// TODO: 10/08/17 check if return boolean
		transportationLotDao.delete(id);
	}

	@Override
	public List<TransportationAliquot> getAliquots() throws DataNotFoundException{
		return transportationLotDao.getAliquots();		
	}


}
