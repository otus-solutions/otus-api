package br.org.otus.report;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.RequestParameters;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ReportFacadeTest.class)
public class ReportFacadeTest {

	@InjectMocks
	private ReportFacade reportFacade;

	@Mock
	private ReportService reportService;

	private ObjectId objectId = new ObjectId("5a9199056ddc4f48a340b3ec");

    private String requestParametersJson = "{\"recruitmentNumber\":322148795,\"reportId\":\"5a9199056ddc4f48a340b3ec\"}";

    @Mock
    private RequestParameters requestParameters = RequestParameters.deserialize(requestParametersJson);

    @Test
	public void method_getByReportId_should_return_ReportTemplate() throws DataNotFoundException {
        ParticipantDataSource participantDataSource = new ParticipantDataSource();
        Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
        Whitebox.setInternalState(participantDataSource, "result", new ArrayList<>());

        ReportTemplate reportTemplate = new ReportTemplate();
        String template = "<span>teste</span>";
        Whitebox.setInternalState(reportTemplate, "template", template);
        Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
        reportTemplate.getDataSources().add(participantDataSource);
		when(reportService.findReportById(requestParameters)).thenReturn(reportTemplate);
		when(requestParameters.getReportId()).thenReturn(objectId);
		assertTrue(reportFacade.getByReportId(requestParameters) instanceof ReportTemplate);
	}

    @Test(expected = HttpResponseException.class)
    public void method_getByReportId_should_throw_HttpResponseException()
            throws HttpResponseException, DataNotFoundException {
        doThrow(new DataNotFoundException(new Exception("method_RegisterProject_should_captured_DataNotFoundException"))).when(reportService).findReportById(requestParameters);
        reportFacade.getByReportId(requestParameters);
    }

}
