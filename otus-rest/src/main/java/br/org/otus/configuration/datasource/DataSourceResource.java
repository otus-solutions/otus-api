package br.org.otus.configuration.datasource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.ccem.otus.model.DataSource;
import org.ccem.otus.utils.CsvToJson;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.otus.datasource.api.DataSourceFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

@Path("/configuration/datasources")
public class DataSourceResource {
	
	@Inject
	private DataSourceFacade dataSourceFacade;

	@POST
	@Secured
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String post(@MultipartForm DataSourceFormPOJO form){
		DataSource dataSource = new DataSource(form.getId(), form.getName(), new CsvToJson(form.getDelimiter(), form.getFile()).execute(form.getDelimiter()));
		dataSourceFacade.create(dataSource);
		return new Response().buildSuccess().toJson();
	}

	@PUT
	@Secured
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String put(@MultipartForm DataSourceFormPOJO form){
		DataSource dataSource = new DataSource(form.getId(), form.getName(), new CsvToJson(form.getDelimiter(), form.getFile()).execute(form.getDelimiter()));
		dataSourceFacade.update(dataSource);
		return new Response().buildSuccess().toJson();
	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {
		return new Response().buildSuccess(dataSourceFacade.getAll()).toJson();
	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getByID(@PathParam("id") String id) {
		return new Response().buildSuccess(dataSourceFacade.getByID(id)).toJson();
	}

}
