package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configurationCrud.model.MomentConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface MomentConfigurationDao {
    void create(MomentConfiguration momentConfiguration);

    ArrayList<MomentConfiguration> index() throws DataNotFoundException;
}
