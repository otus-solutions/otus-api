package br.org.otus.extraction.rest;

import br.org.otus.AuthenticationResourceTestsParent;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.user.dto.ManagementUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.user.api.UserFacade;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class, javax.ws.rs.core.Response.class})
public class ExtractionResourceTest extends AuthenticationResourceTestsParent {

  private static final String EXTRACTION_TOKEN = "123";

  @InjectMocks
  private ExtractionResource extractionResource;

  @Mock
  private UserFacade userFacade;
  @Mock
  private ExtractionFacade extractionFacade;
  @Mock
  private ManagementUserDto managementUserDto;


  @Test
  public void extractExamsValues_method_should_verify_method_extractExamsValues_have_been_called() {
    extractionResource.extractExamsValues();
    Mockito.verify(extractionFacade).createLaboratoryExamsValuesExtraction();
  }

  @Test
  public void extractLaboratory_method_should_call_createLaboratoryExtraction_method() {
    extractionResource.extractLaboratory();
    Mockito.verify(extractionFacade).createLaboratoryExtraction();
  }

  @Test
  public void enableUsers_method_should_call_userFacade_enableExtraction_method() {
    String response = extractionResource.enableUsers(managementUserDto);
    Mockito.verify(userFacade).enableExtraction(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void disableUsers_method_should_call_userFacade_disableExtraction_method() {
    String response = extractionResource.disableUsers(managementUserDto);
    Mockito.verify(userFacade).disableExtraction(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void enableIps_method_should_call_userFacade_updateExtractionIps_method() {
    String response = extractionResource.enableIps(managementUserDto);
    Mockito.verify(userFacade).updateExtractionIps(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void getToken_method_should_call_userFacade_getExtractionToken_method() {
    mockContextToSetUserEmail();
    PowerMockito.when(userFacade.getExtractionToken(USER_EMAIL)).thenReturn(EXTRACTION_TOKEN);
    String response = extractionResource.getToken(request);
    Mockito.verify(userFacade).getExtractionToken(USER_EMAIL);
    assertEquals(encapsulateExpectedStringResponse(EXTRACTION_TOKEN), response);
  }

}
