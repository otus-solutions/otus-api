package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class LaboratoryProjectServiceBean implements LaboratoryProjectService{

    @Inject
    TransportationLotService transportationLotService;

    @Override
    public List<TransportationAliquot> getAllAliquots() throws DataNotFoundException {
        return transportationLotService.getAliquots();
    }
}
