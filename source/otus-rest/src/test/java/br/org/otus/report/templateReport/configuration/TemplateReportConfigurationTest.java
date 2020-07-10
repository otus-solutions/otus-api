//package br.org.otus.report.templateReport.configuration;
//
//import br.org.otus.report.ReportFacade;
//import br.org.otus.report.ReportResource;
//import br.org.otus.report.ReportServiceBean;
//import br.org.otus.report.templateReport.TemplateReportFacade;
//import br.org.otus.security.AuthorizationHeaderReader;
//import br.org.otus.security.context.SecurityContext;
//import br.org.otus.security.context.SessionIdentifier;
//import br.org.otus.security.dtos.AuthenticationData;
//import com.google.gson.GsonBuilder;
//import org.bson.types.ObjectId;
//import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
//import org.ccem.otus.model.ActivityReportTemplate;
//import org.ccem.otus.model.ReportTemplate;
//import org.ccem.otus.service.ReportService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ReportTemplate.class, TemplateReportConfiguration.class, AuthorizationHeaderReader.class, GsonBuilder.class})
//public class TemplateReportConfigurationTest {
//
//  private static final Long recruitmentNumber = (Long) 5555563L;
//  private static final String reportId = "5be30a2616da480067523dfd";
//  private ReportTemplate report = PowerMockito.spy(new ReportTemplate());
//
//  @InjectMocks
//  private ReportResource reportResource;
//  @Mock
//  private TemplateReportFacade templateReportFacade;
//  @Mock
//  private ReportService reportService;
//  @Mock
//  private ReportServiceBean reportServiceBean;
//  @Mock
//  private SecurityContext securityContext;
//  @Mock
//  private HttpServletRequest request;
//
//  private ReportTemplate reportTemplate;
//
//  ObjectId id = new ObjectId("5ab128d713cdd20490497f58");
//
//  precisa ser implementado
//  @Test
//  public void method_getReportTemplate_should_return_report_byRecruitmentNumber() throws DataNotFoundException {
//    reportTemplate = new ReportTemplate();
//    Whitebox.setInternalState(reportTemplate, "_id", id);
//    Whitebox.setInternalState(reportTemplate, "label", "label");
//  }
//
//}
