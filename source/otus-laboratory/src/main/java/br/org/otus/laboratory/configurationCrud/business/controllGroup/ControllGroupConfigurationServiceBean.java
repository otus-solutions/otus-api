package br.org.otus.laboratory.configurationCrud.business.controllGroup;

import br.org.otus.laboratory.configurationCrud.model.AliquotConfiguration;
import br.org.otus.laboratory.configurationCrud.model.ControllGroupConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.AliquotConfigurationDao;
import br.org.otus.laboratory.configurationCrud.persistence.ControllGroupConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class ControllGroupConfigurationServiceBean implements ControllGroupConfigurationService {

    @Inject
    private ControllGroupConfigurationDao controllGroupConfigurationDao;

    public void create(ControllGroupConfiguration controllGroupConfiguration) {
        controllGroupConfigurationDao.create(controllGroupConfiguration);
    };

    public ArrayList<ControllGroupConfiguration> index() throws DataNotFoundException {
        return controllGroupConfigurationDao.index();
    }
}
