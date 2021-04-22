package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configurationCrud.model.ControlGroupConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ControlGroupConfigurationDao {
    void create(ControlGroupConfiguration controlGroupConfiguration);

    ArrayList<ControlGroupConfiguration> index() throws DataNotFoundException;
}
