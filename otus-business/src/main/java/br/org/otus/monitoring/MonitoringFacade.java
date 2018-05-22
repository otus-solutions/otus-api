package br.org.otus.monitoring;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.MonitoringService;

import javax.inject.Inject;
import java.util.List;

public class MonitoringFacade {

    @Inject
    private MonitoringService monitoringService;

    public List<MonitoringDataSourceResult> list(){
        try {
            return monitoringService.list();
        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
}
