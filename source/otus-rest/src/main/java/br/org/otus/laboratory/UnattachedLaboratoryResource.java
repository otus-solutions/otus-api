package br.org.otus.laboratory;

import br.org.otus.rest.Response;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import unattached.UnattachedLaboratory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/unattached-laboratory")
public class UnattachedLaboratoryResource {

  @Inject
  private UnattachedLaboratoryFacade unattachedLaboratoryFacade;

  @PUT
  @Path("/create/{acronym}/{descriptor}")
  @Consumes(MediaType.APPLICATION_JSON)
  public synchronized String initialize(@PathParam("acronym") String fieldCenterAcronym, @PathParam("descriptor") String collectGroupDescriptorName) throws DataNotFoundException {
    UnattachedLaboratory laboratory = null;
    laboratory = unattachedLaboratoryFacade.create(fieldCenterAcronym, collectGroupDescriptorName);
    return new Response().buildSuccess(UnattachedLaboratory.serialize(laboratory)).toJson();
  }
}