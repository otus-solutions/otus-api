package br.org.otus.rest;

import br.org.otus.configuration.datasource.DataSourceResource;
import br.org.otus.configuration.publish.TemplateResource;
import br.org.otus.configuration.survey.SurveyResource;
import br.org.otus.configuration.visual.VisualIdentityResource;
import br.org.otus.examUploader.ExamUploadResource;
import br.org.otus.extraction.rest.ExtractionResource;
import br.org.otus.fieldCenter.FieldCenterResource;
import br.org.otus.fileuploader.FileUploaderResource;
import br.org.otus.importation.ParticipantImportationResource;
import br.org.otus.laboratory.ParticipantLaboratoryResource;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationResource;
import br.org.otus.laboratory.project.ExamResource;
import br.org.otus.laboratory.project.TransportationResource;
import br.org.otus.participant.ParticipantResource;
import br.org.otus.security.rest.AuthenticationResource;
import br.org.otus.settings.InstallerResource;
import br.org.otus.survey.activity.ActivityResource;
import br.org.otus.survey.activity.configuration.ActivityConfigurationResource;
import br.org.otus.user.UserResource;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("otus")
public class EndPointsLoader extends Application {

	@Inject
	private InstallerResource installerResource;

	@Inject
	private AuthenticationResource authenticationResource;

	@Inject
	private FieldCenterResource fieldCenterResource;

	@Inject
	private UserResource userResource;

	@Inject
	private SurveyResource surveyResource;
	
	@Inject
	private TemplateResource templateResource;

	@Inject
	private VisualIdentityResource visualIdentityResource;
	
	@Inject
	private ParticipantImportationResource participantImportationResource;
	
	@Inject
	private ParticipantResource participantResource;
	
	@Inject
	private ActivityResource activityResource;

	@Inject
	private ParticipantLaboratoryResource laboratoryParticipantResource;

	@Inject
	private DataSourceResource dataSourceResource;
	
	@Inject
	private FileUploaderResource fileUploaderResource;

	@Inject
	private TransportationResource transportationResource;

	@Inject
	private LaboratoryConfigurationResource laboratoryConfigurationResource;

	@Inject
	private ExtractionResource extractionResource;

	@Inject
	private ActivityConfigurationResource activityConfigurationResource;

	@Inject
	private ExamResource examResource;

	@Inject
	private ExamUploadResource examUploadResource;

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(InstallerResource.class);
		resources.add(AuthenticationResource.class);
		resources.add(FieldCenterResource.class);
		resources.add(UserResource.class);
		resources.add(SurveyResource.class);
		resources.add(TemplateResource.class);
		resources.add(VisualIdentityResource.class);
		resources.add(ParticipantImportationResource.class);
		resources.add(ParticipantResource.class);
		resources.add(ActivityResource.class);
		resources.add(ParticipantLaboratoryResource.class);
		resources.add(DataSourceResource.class);
		resources.add(FileUploaderResource.class);
		resources.add(TransportationResource.class);
		resources.add(LaboratoryConfigurationResource.class);
		resources.add(ExtractionResource.class);
		resources.add(ActivityConfigurationResource.class);
		resources.add(ExamResource.class);
		resources.add(ExamUploadResource.class);
		return resources;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> resources = new HashSet<Object>();
		resources.add(installerResource);
		resources.add(authenticationResource);
		resources.add(fieldCenterResource);
		resources.add(userResource);
		resources.add(surveyResource);
		resources.add(templateResource);
		resources.add(visualIdentityResource);
		resources.add(participantImportationResource);
		resources.add(participantResource);
		resources.add(activityResource);
		resources.add(laboratoryParticipantResource);
		resources.add(dataSourceResource);
		resources.add(fileUploaderResource);
		resources.add(transportationResource);
		resources.add(laboratoryConfigurationResource);
		resources.add(extractionResource);
		resources.add(activityConfigurationResource);
		resources.add(examResource);
		resources.add(examUploadResource);

		return resources;
	}
}
