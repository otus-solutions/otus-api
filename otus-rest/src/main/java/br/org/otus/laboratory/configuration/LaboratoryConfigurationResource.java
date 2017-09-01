package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/laboratory-configuration")
public class LaboratoryConfigurationResource {

	@Inject
	private LaboratoryConfigurationService laboratoryConfigurationService;


	@GET
	@Secured
	@Path("/descriptor")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDescriptor() {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationService.getLaboratoryConfiguration();
		LaboratoryConfigurationDTO laboratoryConfigurationDTO = new LaboratoryConfigurationDTO(laboratoryConfiguration);
		return new Response().buildSuccess(laboratoryConfigurationDTO).toJson();
	}

	@GET
	@Secured
	@Path("/aliquot-configuration")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAliquotConfiguration() {
		AliquotConfiguration aliquotConfiguration = laboratoryConfigurationService.getAliquotConfiguration();
		return new Response().buildSuccess(aliquotConfiguration).toJson();
	}

	@GET
	@Secured
	@Path("/aliquot-descriptors")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAliquotDescriptors() {
		List<AliquoteDescriptor> aliquoteDescriptors = laboratoryConfigurationService.getAliquotDescriptors();
		return new Response().buildSuccess(aliquoteDescriptors).toJson();
	}
}
