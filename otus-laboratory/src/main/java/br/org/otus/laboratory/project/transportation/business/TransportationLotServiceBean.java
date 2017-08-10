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
	public TransportationLot update(TransportationLot transportationLot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransportationLot> list(String fieldCenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
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
