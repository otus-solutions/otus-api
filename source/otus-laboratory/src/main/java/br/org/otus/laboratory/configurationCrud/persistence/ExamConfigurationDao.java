package br.org.otus.laboratory.configurationCrud.persistence;

import br.org.otus.laboratory.configurationCrud.model.ExamConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ExamConfigurationDao {
    void create(ExamConfiguration examConfiguration);

    ArrayList<ExamConfiguration> index() throws DataNotFoundException;
}
