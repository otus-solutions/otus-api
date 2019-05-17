package br.org.otus.permission;

import br.org.otus.persistence.UserDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionDao;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;
import org.ccem.otus.persistence.SurveyGroupDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UserPermissionGenericDaoBean.class })
public class UserPermissionGenericDaoBeanTest {
    private static String USER_EMAIL = "teste@gmail.com";
    private static final String DEFAULT_PROFILE = "DEFAULT";
    private static final String SURVEY_GROUP_NAME = "GROUP";

    @InjectMocks
    private UserPermissionGenericDaoBean userPermissionGenericDaoBean;

    @Mock
    private UserDao userDao;

    @Mock
    private UserPermissionDao userPermissionDao;

    @Mock
    private UserPermissionProfileDao userPermissionProfileDao;

    @Mock
    private UserPermissionDTO userPermissionDTO;

    @Mock
    private SurveyGroupDao surveyGroupDao;

    @Test
    public void getUserPermissions_should_call_concatenatePermissions() throws Exception {
        when(userPermissionDao.getAll(USER_EMAIL)).thenReturn(userPermissionDTO);
        when(userPermissionProfileDao.getProfile(DEFAULT_PROFILE)).thenReturn(userPermissionDTO);
        userPermissionGenericDaoBean.getUserPermissions(USER_EMAIL);
        Mockito.verify(userPermissionDTO, Mockito.times(1)).concatenatePermissions(userPermissionDTO);
    }

    @Test
    public void savePermission_should_call_userPermissionDao_savePermission_on_custom_permission() throws Exception {
        SurveyGroupPermission surveyGroupPermission = SurveyGroupPermission.deserialize("{groups:['teste'],email:'teste@gmail.com'}");
        when(userDao.emailExists(USER_EMAIL)).thenReturn(true);
        when(userPermissionProfileDao.getProfile(DEFAULT_PROFILE)).thenReturn(userPermissionDTO);
        userPermissionGenericDaoBean.savePermission(surveyGroupPermission);
        Mockito.verify(userPermissionDao, Mockito.times(1)).savePermission(surveyGroupPermission);
    }

    @Test(expected = DataNotFoundException.class)
    public void savePermission_should_throw_DataNotFound_whem_user_does_not_exists() throws Exception {
        SurveyGroupPermission surveyGroupPermission = SurveyGroupPermission.deserialize("{groups:['teste'],email:'teste@gmail.com'}");
        when(userDao.emailExists(USER_EMAIL)).thenReturn(false);
        when(userPermissionProfileDao.getProfile(DEFAULT_PROFILE)).thenReturn(userPermissionDTO);
        userPermissionGenericDaoBean.savePermission(surveyGroupPermission);
        Mockito.verify(userPermissionDao, Mockito.times(1)).savePermission(surveyGroupPermission);
    }

    @Test
    public void savePermission_should_call_userPermissionDao_delete_on_default_permission() throws Exception {
        SurveyGroupPermission surveyGroupPermission = SurveyGroupPermission.deserialize("{groups:['teste'],email:'teste@gmail.com'}");
        when(userDao.emailExists(USER_EMAIL)).thenReturn(true);
        ArrayList<Permission> permissionArrayList = new ArrayList<>();
        permissionArrayList.add(surveyGroupPermission);
        UserPermissionDTO userPermissionDTO = UserPermissionDTO.deserialize("{permissions:[{objectType:'SurveyGroupPermission',groups:['teste']}]}");
        when(userPermissionProfileDao.getProfile(DEFAULT_PROFILE)).thenReturn(userPermissionDTO);
        userPermissionGenericDaoBean.savePermission(surveyGroupPermission);
        Mockito.verify(userPermissionDao, Mockito.times(1)).deletePermission(surveyGroupPermission);
    }

    @Test
    public void getUserPermittedSurveys_should_build_PermittedSurveys_Array_with_custom_permission() throws Exception {
        SurveyGroupPermission surveyGroupPermission = SurveyGroupPermission.deserialize("{groups:['teste'],email:'teste@gmail.com'}");
        when(userPermissionDao.getGroupPermission(USER_EMAIL)).thenReturn(surveyGroupPermission);
        ArrayList<String> surveyGroups = new ArrayList<>();
        when(surveyGroupDao.getUserPermittedSurveys(surveyGroupPermission.getGroups())).thenReturn(surveyGroups);
        when(surveyGroupDao.getOrphanSurveys()).thenReturn(new ArrayList<>());
        userPermissionGenericDaoBean.getUserPermittedSurveys(USER_EMAIL);
        Mockito.verify(userPermissionDao, Mockito.times(1)).getGroupPermission(USER_EMAIL);
        Mockito.verify(userPermissionProfileDao, Mockito.times(0)).getGroupPermission(DEFAULT_PROFILE);
        Mockito.verify(surveyGroupDao, Mockito.times(1)).getUserPermittedSurveys(surveyGroupPermission.getGroups());
        Mockito.verify(surveyGroupDao, Mockito.times(1)).getOrphanSurveys();
    }

    @Test
    public void getUserPermittedSurveys_should_build_PermittedSurveys_Array_with_default_permission() throws Exception {
        SurveyGroupPermission surveyGroupPermission = SurveyGroupPermission.deserialize("{groups:['teste']}");
        when(userPermissionDao.getGroupPermission(USER_EMAIL)).thenReturn(null);
        when(userPermissionProfileDao.getGroupPermission(DEFAULT_PROFILE)).thenReturn(surveyGroupPermission);
        ArrayList<String> surveyGroups = new ArrayList<>();
        when(surveyGroupDao.getUserPermittedSurveys(surveyGroupPermission.getGroups())).thenReturn(surveyGroups);
        when(surveyGroupDao.getOrphanSurveys()).thenReturn(new ArrayList<>());
        userPermissionGenericDaoBean.getUserPermittedSurveys(USER_EMAIL);
        Mockito.verify(userPermissionDao, Mockito.times(1)).getGroupPermission(USER_EMAIL);
        Mockito.verify(userPermissionProfileDao, Mockito.times(1)).getGroupPermission(DEFAULT_PROFILE);
        Mockito.verify(surveyGroupDao, Mockito.times(1)).getUserPermittedSurveys(surveyGroupPermission.getGroups());
        Mockito.verify(surveyGroupDao, Mockito.times(1)).getOrphanSurveys();
    }

    @Test
    public void removeFromPermissions_should_call_userPermissionDao_removeFromPermissions_and_userPermissionProfileDao_removeFromPermissionsProfile() throws Exception {
        userPermissionGenericDaoBean.removeFromPermissions(SURVEY_GROUP_NAME);
        Mockito.verify(userPermissionDao, Mockito.times(1)).removeFromPermissions(SURVEY_GROUP_NAME);
        Mockito.verify(userPermissionProfileDao, Mockito.times(1)).removeFromPermissionsProfile(SURVEY_GROUP_NAME);
    }
}