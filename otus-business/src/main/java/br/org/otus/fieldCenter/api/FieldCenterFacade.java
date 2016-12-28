package br.org.otus.fieldCenter.api;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.service.FieldCenterService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class FieldCenterFacade {

    @Inject
    private FieldCenterService fieldCenterService;

    public void create(FieldCenter fieldCenter) {
        try {
            fieldCenterService.create(fieldCenter);

        } catch (AlreadyExistException e) {
            throw new HttpResponseException(ResponseBuild.FieldCenter.AlreadyExist.build());

        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build());
        }
    }

    public List<FieldCenter> list() {
        return fieldCenterService.list();
    }

    public void update(FieldCenter fieldCenterUpdateDto){
        try {
            fieldCenterService.update(fieldCenterUpdateDto);

        } catch (ValidationException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build());
        }
    }
}
