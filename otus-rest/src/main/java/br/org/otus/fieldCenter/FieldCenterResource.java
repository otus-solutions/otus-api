package br.org.otus.fieldCenter;

import br.org.otus.fieldCenter.api.FieldCenterFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import org.ccem.otus.model.FieldCenter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/center")
public class FieldCenterResource {
    @Inject
    private FieldCenterFacade fieldCenterFacade;

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(FieldCenter fieldCenter) {
        fieldCenterFacade.create(fieldCenter);
        return new Response().buildSuccess().toJson();
    }

    @GET
    @Secured
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String list() {
        Response response = new Response();
        List<FieldCenter> fieldCenters = fieldCenterFacade.list();
        return response.setData(fieldCenters).toJson();
    }

    @POST
    @Secured
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(FieldCenter fieldCenter) {
        fieldCenterFacade.update(fieldCenter);
        return new Response().buildSuccess().toJson();
    }
}
