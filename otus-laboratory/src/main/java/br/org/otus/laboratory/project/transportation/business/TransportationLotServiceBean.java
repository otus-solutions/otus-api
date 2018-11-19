package br.org.otus.laboratory.project.transportation.business;

import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.validators.TransportationLotValidator;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransportationLotServiceBean implements TransportationLotService {

  @Inject
  private TransportationLotDao transportationLotDao;
  @Inject
  private AliquotDao aliquotDao;

  @Override
  public TransportationLot create(TransportationLot transportationLot, String email) throws ValidationException, DataNotFoundException {
    _validateLot(transportationLot);
    transportationLot.setOperator(email);
    ArrayList<String> aliquotCodeList = transportationLot.getAliquotCodeList();

    ObjectId transportationLotId = transportationLotDao.persist(transportationLot);

    if(!aliquotCodeList.isEmpty()) {
      aliquotDao.addToTransportationLot(aliquotCodeList, transportationLotId);
    }
    return transportationLot;
  }

  @Override
  public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException, ValidationException {
    _validateLot(transportationLot);
    TransportationLot oldTransportationLot = transportationLotDao.findByCode(transportationLot.getCode());
    aliquotDao.updateTransportationLotId(transportationLot.getAliquotCodeList(),oldTransportationLot.getLotId());
    TransportationLot updateResult = transportationLotDao.update(transportationLot);
    return updateResult;
  }

  @Override
  public List<TransportationLot> list() {
    return transportationLotDao.find();
  }

  @Override
  public void delete(String code) throws DataNotFoundException {
    TransportationLot transportationLot = transportationLotDao.findByCode(code);
    aliquotDao.updateTransportationLotId(new ArrayList<>(), (ObjectId) transportationLot.getLotId());

    transportationLotDao.delete(code);
  }

  private void _validateLot(TransportationLot transportationLot) throws ValidationException {
    TransportationLotValidator transportationLotValidator = new TransportationLotValidator(transportationLotDao, transportationLot);
    transportationLotValidator.validate();
  }
}
