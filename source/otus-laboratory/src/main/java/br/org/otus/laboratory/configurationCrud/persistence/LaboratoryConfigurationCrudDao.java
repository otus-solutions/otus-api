package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;

public interface LaboratoryConfigurationCrudDao {
  LaboratoryConfiguration persistConfiguration(LaboratoryConfiguration laboratoryConfiguration);
}
