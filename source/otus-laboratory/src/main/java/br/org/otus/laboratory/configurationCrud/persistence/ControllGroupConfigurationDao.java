package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configurationCrud.model.ControllGroupConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ControllGroupConfigurationDao {
    void create(ControllGroupConfiguration controllGroupConfiguration);

    ArrayList<ControllGroupConfiguration> index() throws DataNotFoundException;
}
