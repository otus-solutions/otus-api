package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface LaboratoryProjectService {

    public List<TransportationAliquot> getAllAliquots() throws DataNotFoundException;
}
