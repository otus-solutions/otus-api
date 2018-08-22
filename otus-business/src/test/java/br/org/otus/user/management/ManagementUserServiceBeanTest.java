package br.org.otus.user.management;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.management.DisableUserNotificationEmail;
import br.org.otus.email.user.management.EnableUserNotificationEmail;
import br.org.otus.email.user.management.PasswordResetEmail;
import br.org.otus.model.User;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.UserDaoBean;
import br.org.otus.user.dto.FieldCenterDTO;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.PasswordResetDto;
import br.org.owail.sender.email.Sender;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ManagementUserServiceBean.class })
public class ManagementUserServiceBeanTest {
  private static final String FIELD_CENTER_ATTRIBUITE = "fieldCenter";
  private static final String ACRONYM_ATTRIBUITE = "acronym";
  private static final String FIELD_CENTER_ACRONYM = "RS";
  private static final String FIELD_CENTER_WITHOUT_ACRONYM = "";
  private static final String EMAIL = "otus@otus.com";
  private static final String NAME = "João";
  private static final String PASSWORD = "123456";
  private static String IP = "192.168.0.1";

  @InjectMocks
  private ManagementUserServiceBean managementUserServiceBean = PowerMockito.spy(new ManagementUserServiceBean());
  @Mock
  private UserDaoBean userDao;
  @Mock
  private EmailNotifierService emailNotifierService;
  @Mock
  private ManagementUserDto managementUserDto;
  @Mock
  private User user;
  @Mock
  private Sender sender;
  @Mock
  private EnableUserNotificationEmail enableUserNotification;
  @Mock
  private DisableUserNotificationEmail disableUserNotification;
  private User userManager;
  @Mock
  private FieldCenterDTO fieldCenterDTO;
  @Mock
  private FieldCenterDao fieldCenterDao;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private PasswordResetRequestDto requestData;
  @Mock
  private PasswordResetEmail passwordResetEmail;
  @Mock
  private PasswordResetDto passwordResetDto;
  @Mock
  private SecurityFacade securityFacade;

  @Before
  public void setUp() throws DataNotFoundException {
    userManager = new User();
    userManager.setName(NAME);
    when(managementUserDto.getEmail()).thenReturn(EMAIL);
    when(managementUserServiceBean.fetchByEmail(EMAIL)).thenReturn(user);
    Whitebox.setInternalState(managementUserDto, FIELD_CENTER_ATTRIBUITE, fieldCenterDTO);
    when(fieldCenterDao.fetchByAcronym(Mockito.any())).thenReturn(fieldCenter);
  }

  @Test
  public void method_enable_should_fetch_user_by_email()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enable(managementUserDto);
    Mockito.verify(userDao).fetchByEmail(EMAIL);
  }

  @Test
  public void method_enable_should_change_status_to_enable()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enable(managementUserDto);
    Mockito.verify(user).enable();
  }

  @Test
  public void method_enable_should_update_user()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enable(managementUserDto);
    Mockito.verify(userDao).update(user);
  }

  @Test
  public void method_enable_should_send_email_to_user() throws Exception {
    PowerMockito.whenNew(EnableUserNotificationEmail.class).withNoArguments().thenReturn(enableUserNotification);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(emailNotifierService.getSender()).thenReturn(sender);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);

    managementUserServiceBean.enable(managementUserDto);
    Mockito.verify(emailNotifierService).sendEmail(enableUserNotification);
  }

  @Test
  public void method_disable_should_fetch_user_by_email()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disable(managementUserDto);
    Mockito.verify(userDao).fetchByEmail(EMAIL);
  }

  @Test
  public void method_disable_should_change_status_to_enable()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disable(managementUserDto);
    Mockito.verify(user).disable();
  }

  @Test
  public void method_disable_should_update_user()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disable(managementUserDto);
    Mockito.verify(userDao).update(user);
  }

  @Test
  public void method_disable_should_send_email_to_user() throws Exception {
    Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
    PowerMockito.whenNew(DisableUserNotificationEmail.class).withNoArguments().thenReturn(disableUserNotification);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(emailNotifierService.getSender()).thenReturn(sender);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);

    managementUserServiceBean.disable(managementUserDto);
    Mockito.verify(emailNotifierService).sendEmail(disableUserNotification);
  }

  @Test
  public void method_disable_should_verify_if_is_admin()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(user.isAdmin()).thenReturn(Boolean.TRUE);
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);

    managementUserServiceBean.disable(managementUserDto);
    Mockito.verify(user).isAdmin();
    Mockito.verify(userDao, Mockito.never()).update(user);
  }

  @Test(expected = ValidationException.class)
  public void method_enable_should_throw_ValidationException_when_dto_invalid()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
    managementUserServiceBean.enable(managementUserDto);
  }

  @Test(expected = ValidationException.class)
  public void method_disable_should_throw_ValidationException_when_dto_invalid()
      throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
    managementUserServiceBean.disable(managementUserDto);
  }

  @Test
  public void method_enableExtraction_should_fetch_user_by_email() throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enableExtraction(managementUserDto);
    Mockito.verify(userDao).fetchByEmail(EMAIL);
  }

  @Test
  public void method_enableExtraction_should_change_status_to_enable()
      throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enableExtraction(managementUserDto);
    Mockito.verify(user).enableExtraction();
  }

  @Test
  public void method_enableExtraction_should_update_user() throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.enableExtraction(managementUserDto);
    Mockito.verify(userDao).update(user);
  }

  @Test(expected = ValidationException.class)
  public void method_enableExtraction_should_throw_ValidationException_when_dto_invalid()
      throws ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
    managementUserServiceBean.enableExtraction(managementUserDto);
  }

  @Test
  public void method_disableExtraction_should_fetch_user_by_email() throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disableExtraction(managementUserDto);
    Mockito.verify(userDao).fetchByEmail(EMAIL);
  }

  @Test
  public void method_disableExtraction_should_change_status_to_disabled()
      throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disableExtraction(managementUserDto);
    Mockito.verify(user).disableExtraction();
  }

  @Test
  public void method_disableExtraction_should_update_user() throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserServiceBean.disableExtraction(managementUserDto);
    Mockito.verify(userDao).update(user);
  }

  @Test(expected = ValidationException.class)
  public void method_disableExtraction_should_throw_ValidationException_when_dto_invalid()
      throws ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
    managementUserServiceBean.disableExtraction(managementUserDto);
  }

  @Test
  public void method_updateExtractionIps_should_update_user() throws ValidationException, DataNotFoundException {
    Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
    Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
    managementUserDto.extractionIps = new ArrayList();
    managementUserDto.extractionIps.add(IP);
    managementUserServiceBean.updateExtractionIps(managementUserDto);
    Mockito.verify(userDao).update(user);
  }

  @Test(expected = ValidationException.class)
  public void method_updateExtractionIps_should_throw_ValidationException_when_dto_invalid()
      throws ValidationException, DataNotFoundException {
    Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
    managementUserServiceBean.updateExtractionIps(managementUserDto);
  }

  @Test
  public void listMethod_should_return_administrationUsersDtos() throws Exception {
    List<User> users = Arrays.asList(userManager);
    when(userDao.fetchAll()).thenReturn(users);
    assertEquals(NAME, managementUserServiceBean.list().get(0).name);
    verify(userDao, times(1)).fetchAll();
  }

  @Test
  public void updateFieldCenterMethod_should_invoke_update_of_UserDao_with_acronymFieldCenter() throws Exception {
    Whitebox.setInternalState(fieldCenterDTO, ACRONYM_ATTRIBUITE, FIELD_CENTER_ACRONYM);
    managementUserServiceBean.updateFieldCenter(managementUserDto);
    verify(userDao, times(1)).update(user);
    verify(user, times(1)).setFieldCenter(fieldCenter);
    verify(user, times(0)).setFieldCenter(null);
  }

  @Test
  public void updateFieldCenterMethod_should_invoke_update_of_UserDao_without_acronymFieldCenter() throws Exception {
    Whitebox.setInternalState(fieldCenterDTO, ACRONYM_ATTRIBUITE, FIELD_CENTER_WITHOUT_ACRONYM);
    managementUserServiceBean.updateFieldCenter(managementUserDto);
    verify(userDao, times(1)).update(user);
    verify(user, times(0)).setFieldCenter(fieldCenter);
    verify(user, times(1)).setFieldCenter(null);
  }

  @Test
  public void requestPasswordResetMethod_should_invoke_internal_methods() throws Exception {
    whenNew(PasswordResetEmail.class).withAnyArguments().thenReturn(passwordResetEmail);
    when(requestData.getEmail()).thenReturn(EMAIL);
    when(emailNotifierService.getSender()).thenReturn(sender);
    managementUserServiceBean.requestPasswordReset(requestData);
    verify(passwordResetEmail, times(1)).defineRecipient(EMAIL);
    verify(passwordResetEmail, times(1)).setFrom(sender);
    verify(emailNotifierService, times(1)).sendEmail(passwordResetEmail);
  }

  @Test
  public void updateUserPasswordMethod_invoke_internal_methods() throws EncryptedException {
    when(passwordResetDto.getEmail()).thenReturn(EMAIL);
    when(passwordResetDto.getPassword()).thenReturn(PASSWORD);
    managementUserServiceBean.updateUserPassword(passwordResetDto);
    verify(passwordResetDto, times(1)).encrypt();
    verify(userDao, times(1)).updatePassword(EMAIL, PASSWORD);
    verify(securityFacade, times(1)).removePasswordResetRequests(EMAIL);
  }
}
