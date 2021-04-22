package br.org.otus.laboratory.configurationCrud.business.exam;

import br.org.otus.laboratory.configurationCrud.model.ExamConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.ExamConfigurationDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class ExamConfigurationServiceBean implements ExamConfigurationService {

    @Inject
    private ExamConfigurationDao examConfigurationDao;

    public void create(ExamConfiguration examConfiguration) {
        examConfigurationDao.create(examConfiguration);
    };

    public ArrayList<ExamConfiguration> index() throws DataNotFoundException {
        return examConfigurationDao.index();
    }
}
