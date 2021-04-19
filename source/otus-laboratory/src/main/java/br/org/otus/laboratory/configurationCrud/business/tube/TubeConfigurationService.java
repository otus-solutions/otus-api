package br.org.otus.laboratory.configurationCrud.business.tube;

import br.org.otus.laboratory.configurationCrud.model.TubeConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface TubeConfigurationService {
    void create(TubeConfiguration tubeConfiguration);

    ArrayList<TubeConfiguration> index() throws DataNotFoundException;
}
