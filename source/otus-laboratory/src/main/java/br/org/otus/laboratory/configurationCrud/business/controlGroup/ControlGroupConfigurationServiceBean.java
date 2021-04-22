package br.org.otus.laboratory.configurationCrud.business.controlGroup;

import br.org.otus.laboratory.configurationCrud.model.ControlGroupConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.ControlGroupConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class ControlGroupConfigurationServiceBean implements ControlGroupConfigurationService {

    @Inject
    private ControlGroupConfigurationDao controlGroupConfigurationDao;

    public void create(ControlGroupConfiguration controlGroupConfiguration) {
        controlGroupConfigurationDao.create(controlGroupConfiguration);
    };

    public ArrayList<ControlGroupConfiguration> index() throws DataNotFoundException {
        return controlGroupConfigurationDao.index();
    }
}
