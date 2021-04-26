package br.org.otus.laboratory.configurationCrud.business;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.LaboratoryConfigurationCrudDao;

@Stateless
public class LaboratoryConfigurationCrudServiceBean implements LaboratoryConfigurationCrudService {
    @Inject
    LaboratoryConfigurationCrudDao laboratoryConfigurationCrudDao;

    public LaboratoryConfiguration persistConfiguration(LaboratoryConfiguration laboratoryConfiguration) {
        return this.laboratoryConfigurationCrudDao.persistConfiguration(laboratoryConfiguration);
    };
}
