package br.org.otus.laboratory.configurationCrud.business.controlGroup;

import br.org.otus.laboratory.configurationCrud.model.ControlGroupConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ControlGroupConfigurationService {
    void create(ControlGroupConfiguration controlGroupConfiguration);

    ArrayList<ControlGroupConfiguration> index() throws DataNotFoundException;
}
