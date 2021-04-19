package br.org.otus.laboratory.configurationCrud.business.moment;

import br.org.otus.laboratory.configurationCrud.model.MomentConfiguration;
import br.org.otus.laboratory.configurationCrud.model.TubeConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.MomentConfigurationDao;
import br.org.otus.laboratory.configurationCrud.persistence.TubeConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class MomentConfigurationServiceBean implements MomentConfigurationService {

    @Inject
    private MomentConfigurationDao momentConfigurationDao;

    public void create(MomentConfiguration momentConfiguration) {
        momentConfigurationDao.create(momentConfiguration);
    };

    public ArrayList<MomentConfiguration> index() throws DataNotFoundException {
        return momentConfigurationDao.index();
    }
}
