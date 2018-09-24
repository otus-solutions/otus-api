package br.org.otus.user;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.api.UserFacade;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.PasswordResetDto;
import br.org.otus.user.dto.SignupDataDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ManagementUserDto.class })
public class UserResourceTest {

  private static final String RESPONSE_VALID = "{\"data\":true}";
  private static final String RESPONSE_LIST = "{\"data\":[{\"fieldCenter\":{}}]}";
  private static final String TOKEN = "123456";
  @InjectMocks
  private UserResource resource;
  @Mock
  private UserFacade userFacade;
  @Mock
  private SignupDataDto signupDataDto;

  private ManagementUserDto managementUserDto = new ManagementUserDto();
  @Mock
  private PasswordResetRequestDto requestData;
  @Mock
  private PasswordResetDto resetData;

  @Test
  public void signupMethod_should_invoke_create_and_encrypt_methods() throws EncryptedException {
    resource.signup(signupDataDto);
    verify(signupDataDto, times(1)).encrypt();
    verify(userFacade, times(1)).create(signupDataDto);
  }

  @Test
  public void signupMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.signup(signupDataDto));
  }

  @Test(expected = HttpResponseException.class)
  public void signupMethod_should_should_capture_EncryptedException() throws EncryptedException {
    doThrow(new EncryptedException()).when(signupDataDto).encrypt();
    resource.signup(signupDataDto);
  }

  @Test
  public void listMethod_should_invoke_list_of_UserFacade() {
    resource.list();
    verify(userFacade, times(1)).list();
  }

  @Test
  public void listMethod_should_return_response_containing_managementUserDtos() {
    List<ManagementUserDto> managementUserDtos = Arrays.asList(managementUserDto);
    PowerMockito.when(userFacade.list()).thenReturn(managementUserDtos);
    assertEquals(RESPONSE_LIST, resource.list());
  }

  @Test
  public void disableUsersMethod_should_evoke_disable_of_UserFacade() {
    resource.disableUsers(managementUserDto);
    verify(userFacade, times(1)).disable(managementUserDto);
  }

  @Test
  public void disableUsersMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.disableUsers(managementUserDto));
  }

  @Test
  public void updateFieldCenterMethod_should_evoke_disable_of_UserFacade() {
    resource.updateFieldCenter(managementUserDto);
    verify(userFacade, times(1)).updateFieldCenter(managementUserDto);
  }

  @Test
  public void updateFieldCenterMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.updateFieldCenter(managementUserDto));
  }

  @Test
  public void enableUsersMethod_should_evoke_enable_of_UserFacade() {
    resource.enableUsers(managementUserDto);
    verify(userFacade, times(1)).enable(managementUserDto);
  }
  
  @Test
  public void enableUsersMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.enableUsers(managementUserDto));
  }
  
  @Test
  public void requestRecoveryMethod_should_evoke_requestPasswordReset_of_UserFacade() {
    resource.requestRecovery(requestData);
    verify(userFacade, times(1)).requestPasswordReset(requestData);
  }
  
  @Test
  public void requestRecoveryMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.requestRecovery(requestData));
  }
  
  @Test
  public void validateTokenMethod_should_evoke_validatePasswordRecoveryRequest_of_UserFacade() {
    resource.validateToken(TOKEN);
    verify(userFacade, times(1)).validatePasswordRecoveryRequest(TOKEN);
  }
  
  @Test
  public void validateTokenMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.validateToken(TOKEN));
  }
  
  @Test
  public void updatePasswordMethod_should_evoke_updateUserPassword_of_UserFacade() {
    resource.updatePassword(resetData);
    verify(userFacade, times(1)).updateUserPassword(resetData);
  }
  
  @Test
  public void updatePasswordMethod_should_return_validResponse() {
    assertEquals(RESPONSE_VALID, resource.updatePassword(resetData));
  }
}
