package br.org.otus.fileuploader;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ccem.otus.model.FileUploaderPOJO;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.security.Secured;

@Path("/upload")
public class FileUploaderResource {

	@Inject
	private FileUploaderFacade facade;

	@POST
	@Secured
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String post(@MultipartForm FileUploaderPOJO form) throws IOException {
		return new br.org.otus.rest.Response().buildSuccess(facade.upload(form)).toJson();
	}

	@POST
//	@Secured todo; uncomment
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getById(String oid) {
		ResponseBuilder builder = Response.ok(facade.getById(oid));
		builder.header("Content-Disposition", "attachment; filename=" + "anything");
		return builder.build();
	}

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response delete(@PathParam("id") String oid) {
		facade.delete(oid);
		return Response.ok().build();
	}

	@POST
//	@Secured todo: uncomment
	@Path("/extraction")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response list(ArrayList<String> oids) {
		ResponseBuilder builder = Response.ok(facade.list(oids));
		builder.header("Content-Disposition", "attachment; filename=" + "anything");
		return builder.build();
	}

}
