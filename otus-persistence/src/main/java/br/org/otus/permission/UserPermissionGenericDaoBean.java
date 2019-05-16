package br.org.otus.permission;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionDao;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;
import org.ccem.otus.persistence.SurveyGroupDao;

import br.org.otus.user.UserDao;

public class UserPermissionGenericDaoBean implements UserPermissionGenericDao {

  private static final String DEFAULT_PROFILE = "DEFAULT";

  @Inject
  private UserPermissionDao userPermissionDao;

  @Inject
  private UserPermissionProfileDao userPermissionProfileDao;

  @Inject
  private SurveyGroupDao surveyGroupDao;

  @Inject
  private UserDao userDao;

  @Override
  public UserPermissionDTO getUserPermissions(String email) throws DataNotFoundException {
    UserPermissionDTO permissionProfile = userPermissionProfileDao.getProfile(DEFAULT_PROFILE);
    UserPermissionDTO userPermissions = userPermissionDao.getAll(email);

    permissionProfile.concatenatePermissions(userPermissions);
    
    return permissionProfile;
  }

  @Override
  public Permission savePermission(Permission permission) throws DataNotFoundException {
    if(!userDao.emailExists(permission.getEmail())){
      throw new DataNotFoundException(new Throwable("User with email: {" + permission.getEmail() + "} not found."));
    }
    UserPermissionDTO permissionProfile = userPermissionProfileDao.getProfile(DEFAULT_PROFILE);
    List<Permission> permissionFound = permissionProfile.getPermissions().stream().filter(profilePermission -> profilePermission.equals(permission)).collect(Collectors.toList());
    if(permissionFound.isEmpty()){
      userPermissionDao.savePermission(permission);
    } else {
      userPermissionDao.deletePermission(permission);
    }
    return permission;
  }

  @Override
  public List<String> getUserPermittedSurveys(String email) {
    SurveyGroupPermission groupPermission;
    groupPermission = userPermissionDao.getGroupPermission(email);
    if (groupPermission == null){
      groupPermission = userPermissionProfileDao.getGroupPermission(DEFAULT_PROFILE);
    }
    Set<String> surveyGroups = groupPermission.getGroups();
    List<String> userPermittedSurveys = surveyGroupDao.getUserPermittedSurveys(surveyGroups);
    userPermittedSurveys.addAll(surveyGroupDao.getOrphanSurveys());

    return userPermittedSurveys;
  }

  @Override
  public void removeFromPermissions(String surveyGroupName) throws DataNotFoundException {
      userPermissionDao.removeFromPermissions(surveyGroupName);
      userPermissionProfileDao.removeFromPermissionsProfile(surveyGroupName);
  }

}
