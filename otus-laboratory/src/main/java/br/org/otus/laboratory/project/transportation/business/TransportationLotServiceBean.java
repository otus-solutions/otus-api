package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TransportationLotServiceBean implements TransportationLotService {

	@Inject
	private TransportationLotDao transportationLotDao;

	@Override
	public TransportationLot create(TransportationLot transportationLot) {
		//generate code - UUID?
//		transportationLot.setCode(code);
		transportationLotDao.persist(transportationLot);
		return transportationLot;
	}

	@Override
	public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException {
		TransportationLot updateResult = transportationLotDao.update(transportationLot);
		return updateResult;

	}

	@Override
	public List<TransportationLot> list(String fieldCenter) {
		return transportationLotDao.find();
	}

	@Override
	public void delete(String id) {
		// TODO: 10/08/17 check if return boolean
		transportationLotDao.delete(id);
	}

	@Override
	public List<TransportationAliquot> getAliquots(){
		try {
			return transportationLotDao.getAliquots();
		} catch (DataNotFoundException e) {
			//throws // TODO: 10/08/17
			e.printStackTrace();
		}
		return null;
		// TODO: 10/08/17 remove
	}


}
