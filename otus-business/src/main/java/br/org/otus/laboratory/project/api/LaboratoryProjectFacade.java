package br.org.otus.laboratory.project.api;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.List;

public class LaboratoryProjectFacade {

    @Inject
    private TransportationLotFacade transportationLotFacade;

    @Inject
    LaboratoryProjectService laboratoryProjectService;

    public List<WorkAliquot> getAllProjectAliquots(){
        try {
            return laboratoryProjectService.getAllAliquots();
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }
}
