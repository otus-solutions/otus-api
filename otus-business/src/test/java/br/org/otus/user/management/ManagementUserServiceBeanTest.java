package br.org.otus.user.management;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.email.user.management.DisableUserNotificationEmail;
import br.org.otus.email.user.management.EnableUserNotificationEmail;
import br.org.otus.model.User;
import br.org.otus.user.UserDaoBean;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.owail.sender.email.Sender;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ManagementUserServiceBean.class})
public class ManagementUserServiceBeanTest {
    private static final String EMAIL = "email@email";
    private static String IP = "192.168.0.1";

    @InjectMocks
    private ManagementUserServiceBean managementUserServiceBean;

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

    @Test
    public void method_enable_should_fetch_user_by_email() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
        managementUserServiceBean.enable(managementUserDto);
        Mockito.verify(userDao).fetchByEmail(EMAIL);
    }

    @Test
    public void method_enable_should_change_status_to_enable() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
        managementUserServiceBean.enable(managementUserDto);
        Mockito.verify(user).enable();
    }

    @Test
    public void method_enable_should_update_user() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
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
    public void method_disable_should_fetch_user_by_email() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
        managementUserServiceBean.disable(managementUserDto);
        Mockito.verify(userDao).fetchByEmail(EMAIL);
    }

    @Test
    public void method_disable_should_change_status_to_enable() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(user.isAdmin()).thenReturn(Boolean.FALSE);
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
        managementUserServiceBean.disable(managementUserDto);
        Mockito.verify(user).disable();
    }

    @Test
    public void method_disable_should_update_user() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
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
    public void method_disable_should_verify_if_is_admin() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(user.isAdmin()).thenReturn(Boolean.TRUE);
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);

        managementUserServiceBean.disable(managementUserDto);
        Mockito.verify(user).isAdmin();
        Mockito.verify(userDao, Mockito.never()).update(user);
    }

    @Test(expected = ValidationException.class)
    public void method_enable_should_throw_ValidationException_when_dto_invalid() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
        managementUserServiceBean.enable(managementUserDto);
    }

    @Test(expected = ValidationException.class)
    public void method_disable_should_throw_ValidationException_when_dto_invalid() throws EmailNotificationException, EncryptedException, ValidationException, DataNotFoundException {
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
    public void method_enableExtraction_should_change_status_to_enable() throws ValidationException, DataNotFoundException {
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
    public void method_enableExtraction_should_throw_ValidationException_when_dto_invalid() throws ValidationException, DataNotFoundException {
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
        managementUserServiceBean.enableExtraction(managementUserDto);
    }

    @Test
    public void method_disableExtraction_should_fetch_user_by_email() throws  ValidationException, DataNotFoundException {
        Mockito.when(userDao.fetchByEmail(EMAIL)).thenReturn(user);
        Mockito.when(managementUserDto.getEmail()).thenReturn(EMAIL);
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.TRUE);
        managementUserServiceBean.disableExtraction(managementUserDto);
        Mockito.verify(userDao).fetchByEmail(EMAIL);
    }

    @Test
    public void method_disableExtraction_should_change_status_to_disabled() throws ValidationException, DataNotFoundException {
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
    public void method_disableExtraction_should_throw_ValidationException_when_dto_invalid() throws ValidationException, DataNotFoundException {
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
    public void method_updateExtractionIps_should_throw_ValidationException_when_dto_invalid() throws ValidationException, DataNotFoundException {
        Mockito.when(managementUserDto.isValid()).thenReturn(Boolean.FALSE);
        managementUserServiceBean.updateExtractionIps(managementUserDto);
    }

}
