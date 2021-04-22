package br.org.otus.laboratory.configurationCrud.business.aliquot;

import br.org.otus.laboratory.configurationCrud.model.AliquotConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface AliquotConfigurationService {
    void create(AliquotConfiguration aliquotConfiguration);

    ArrayList<AliquotConfiguration> index() throws DataNotFoundException;
}
