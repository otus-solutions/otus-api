package br.org.otus.rest;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import br.org.otus.importation.ActivityImportationResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.configuration.datasource.DataSourceResource;
import br.org.otus.configuration.project.ProjectConfigurationResource;
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
import br.org.otus.monitoring.MonitoringResource;
import br.org.otus.participant.ParticipantResource;
import br.org.otus.permission.ActivityPermissionResource;
import br.org.otus.permission.UserPermissionResource;
import br.org.otus.report.ReportResource;
import br.org.otus.security.user.rest.AuthenticationResource;
import br.org.otus.settings.InstallerResource;
import br.org.otus.survey.activity.ActivityResource;
import br.org.otus.survey.activity.configuration.ActivityConfigurationResource;
import br.org.otus.user.UserResource;

@RunWith(PowerMockRunner.class)
public class EndPointsLoaderTest {
  @InjectMocks
  private EndPointsLoader endPointerLoader;
  @Mock
  private ActivityImportationResource activityImportationResource;
  @Mock
  private MonitoringResource monitoringResource;
  @Mock
  private InstallerResource installerResource;
  @Mock
  private AuthenticationResource authenticationResource;
  @Mock
  private FieldCenterResource fieldCenterResource;
  @Mock
  private UserResource userResource;
  @Mock
  private SurveyResource surveyResource;
  @Mock
  private TemplateResource templateResource;
  @Mock
  private VisualIdentityResource visualIdentityResource;
  @Mock
  private ParticipantImportationResource participantImportationResource;
  @Mock
  private ParticipantResource participantResource;
  @Mock
  private ActivityResource activityResource;
  @Mock
  private ReportResource reportResource;
  @Mock
  private ParticipantLaboratoryResource laboratoryParticipantResource;
  @Mock
  private DataSourceResource dataSourceResource;
  @Mock
  private FileUploaderResource fileUploaderResource;
  @Mock
  private TransportationResource transportationResource;
  @Mock
  private LaboratoryConfigurationResource laboratoryConfigurationResource;
  @Mock
  private ExtractionResource extractionResource;
  @Mock
  private ActivityConfigurationResource activityConfigurationResource;
  @Mock
  private ExamResource examResource;
  @Mock
  private ExamUploadResource examUploadResource;
  @Mock
  private ProjectConfigurationResource projectConfigurationResource;
  @Mock
  private ActivityPermissionResource activityAccessPermissionResource;
  @Mock
  private UserPermissionResource userPermissionResource;

  @Test
  public void getClassesMetods_should_check_the_presence_of_classes_within_the_list() {
    Set<Class<?>> resourcesClasses = endPointerLoader.getClasses();
    assertTrue(endPointerLoader.getClasses() instanceof HashSet);
    assertTrue(resourcesClasses.contains(ActivityImportationResource.class));
    assertTrue(resourcesClasses.contains(MonitoringResource.class));
    assertTrue(resourcesClasses.contains(InstallerResource.class));
    assertTrue(resourcesClasses.contains(AuthenticationResource.class));
    assertTrue(resourcesClasses.contains(FieldCenterResource.class));
    assertTrue(resourcesClasses.contains(UserResource.class));
    assertTrue(resourcesClasses.contains(SurveyResource.class));
    assertTrue(resourcesClasses.contains(TemplateResource.class));
    assertTrue(resourcesClasses.contains(VisualIdentityResource.class));
    assertTrue(resourcesClasses.contains(ParticipantImportationResource.class));
    assertTrue(resourcesClasses.contains(ReportResource.class));
    assertTrue(resourcesClasses.contains(ParticipantResource.class));
    assertTrue(resourcesClasses.contains(ActivityResource.class));
    assertTrue(resourcesClasses.contains(ParticipantLaboratoryResource.class));
    assertTrue(resourcesClasses.contains(DataSourceResource.class));
    assertTrue(resourcesClasses.contains(FileUploaderResource.class));
    assertTrue(resourcesClasses.contains(TransportationResource.class));
    assertTrue(resourcesClasses.contains(LaboratoryConfigurationResource.class));
    assertTrue(resourcesClasses.contains(ExtractionResource.class));
    assertTrue(resourcesClasses.contains(ActivityConfigurationResource.class));
    assertTrue(resourcesClasses.contains(ExamResource.class));
    assertTrue(resourcesClasses.contains(ExamUploadResource.class));
    assertTrue(resourcesClasses.contains(ProjectConfigurationResource.class));
    assertTrue(resourcesClasses.contains(ActivityPermissionResource.class));
    assertTrue(resourcesClasses.contains(UserPermissionResource.class));
  }

  @Test
  public void getSingletons() {
    Set<Object> resourcesSingletons = endPointerLoader.getSingletons();
    assertTrue(resourcesSingletons.contains(activityImportationResource));
    assertTrue(resourcesSingletons.contains(monitoringResource));
    assertTrue(resourcesSingletons.contains(installerResource));
    assertTrue(resourcesSingletons.contains(authenticationResource));
    assertTrue(resourcesSingletons.contains(fieldCenterResource));
    assertTrue(resourcesSingletons.contains(userResource));
    assertTrue(resourcesSingletons.contains(surveyResource));
    assertTrue(resourcesSingletons.contains(templateResource));
    assertTrue(resourcesSingletons.contains(visualIdentityResource));
    assertTrue(resourcesSingletons.contains(participantImportationResource));
    assertTrue(resourcesSingletons.contains(participantResource));
    assertTrue(resourcesSingletons.contains(activityResource));
    assertTrue(resourcesSingletons.contains(laboratoryParticipantResource));
    assertTrue(resourcesSingletons.contains(reportResource));
    assertTrue(resourcesSingletons.contains(dataSourceResource));
    assertTrue(resourcesSingletons.contains(fileUploaderResource));
    assertTrue(resourcesSingletons.contains(transportationResource));
    assertTrue(resourcesSingletons.contains(laboratoryConfigurationResource));
    assertTrue(resourcesSingletons.contains(extractionResource));
    assertTrue(resourcesSingletons.contains(activityConfigurationResource));
    assertTrue(resourcesSingletons.contains(examResource));
    assertTrue(resourcesSingletons.contains(examUploadResource));
    assertTrue(resourcesSingletons.contains(projectConfigurationResource));
    assertTrue(resourcesSingletons.contains(activityAccessPermissionResource));
    assertTrue(resourcesSingletons.contains(userPermissionResource));
  }
}
