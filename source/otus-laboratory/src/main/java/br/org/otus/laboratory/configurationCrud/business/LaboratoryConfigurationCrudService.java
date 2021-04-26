package br.org.otus.laboratory.configurationCrud.business;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;

public interface LaboratoryConfigurationCrudService {
    LaboratoryConfiguration persistConfiguration(LaboratoryConfiguration laboratoryConfiguration);
}
