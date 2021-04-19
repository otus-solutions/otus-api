package br.org.otus.laboratory.configurationCrud.business.tube;

import br.org.otus.laboratory.configurationCrud.model.TubeConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.TubeConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class TubeConfigurationServiceBean implements TubeConfigurationService {

    @Inject
    private TubeConfigurationDao tubeConfigurationDao;

    public void create(TubeConfiguration tubeConfiguration) {
        tubeConfigurationDao.create(tubeConfiguration);
    };

    public ArrayList<TubeConfiguration> index() throws DataNotFoundException {
        return tubeConfigurationDao.index();
    }
}
