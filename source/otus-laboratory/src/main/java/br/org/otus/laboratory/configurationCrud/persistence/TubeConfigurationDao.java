package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configurationCrud.model.TubeConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface TubeConfigurationDao {
    void create(TubeConfiguration tubeConfiguration);

    ArrayList<TubeConfiguration> index() throws DataNotFoundException;
}
