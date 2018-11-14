package br.org.otus.laboratory.participant.aliquot.business;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.validators.TransportationLotValidator;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AliquotServiceBean implements AliquotService {

  @Inject
  private AliquotDao aliquotDao;

  @Inject
  private TransportationLotDao  transportationLotDao;


  @Override
  public List<Aliquot> getAliquots(){
    return aliquotDao.getAliquots();
  }

  @Override
  public List<Aliquot> getAliquots(Long rn) {
    return aliquotDao.list(rn);
  }

  @Override
  public void create(Aliquot aliquot) {

  }

  @Override
  public void create(List<Aliquot> aliquotList) {

  }

  @Override
  public Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) throws DataNotFoundException, ValidationException {
    Aliquot aliquot = aliquotDao.getAliquot(transportationAliquotFiltersDTO);
    if(aliquot.getTransportationLotId() != null)
      throw new ValidationException(new Throwable("There are aliquots in another lot."), transportationAliquotFiltersDTO.getCode());
    return aliquot;
  }

  @Override
  public List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) throws DataNotFoundException {
    return aliquotDao.getAliquotsByPeriod(transportationAliquotFiltersDTO);
  }
  
  @Override
  public boolean exists (String code) {
    return aliquotDao.exists(code);
  }
  
}
