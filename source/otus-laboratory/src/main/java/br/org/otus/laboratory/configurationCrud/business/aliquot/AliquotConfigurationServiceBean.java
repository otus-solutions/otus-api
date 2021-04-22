package br.org.otus.laboratory.configurationCrud.business.aliquot;

import br.org.otus.laboratory.configurationCrud.model.AliquotConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.AliquotConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class AliquotConfigurationServiceBean implements AliquotConfigurationService {

    @Inject
    private AliquotConfigurationDao aliquotConfigurationDao;

    public void create(AliquotConfiguration aliquotConfiguration) {
        aliquotConfigurationDao.create(aliquotConfiguration);
    };

    public ArrayList<AliquotConfiguration> index() throws DataNotFoundException {
        return aliquotConfigurationDao.index();
    }
}
