package br.org.otus.laboratory.participant.aliquot.business;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface AliquotService {

    List<Aliquot> getAliquots();

	List<Aliquot> getAliquots(Long rn);

	void create (Aliquot aliquot);

	void create (List<Aliquot> aliquotList);

	Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) throws ValidationException, DataNotFoundException;
        
	List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) throws DataNotFoundException;
	
	boolean exists(String code);

}
