package br.org.otus.laboratory.configurationCrud.business.controllGroup;

import br.org.otus.laboratory.configurationCrud.model.ControllGroupConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ControllGroupConfigurationService {
    void create(ControllGroupConfiguration controllGroupConfiguration);

    ArrayList<ControllGroupConfiguration> index() throws DataNotFoundException;
}
