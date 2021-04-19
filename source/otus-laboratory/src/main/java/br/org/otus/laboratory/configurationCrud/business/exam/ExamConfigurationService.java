package br.org.otus.laboratory.configurationCrud.business.exam;

import br.org.otus.laboratory.configurationCrud.model.ExamConfiguration;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public interface ExamConfigurationService {
    void create(ExamConfiguration examConfiguration);

    ArrayList<ExamConfiguration> index() throws DataNotFoundException;
}
