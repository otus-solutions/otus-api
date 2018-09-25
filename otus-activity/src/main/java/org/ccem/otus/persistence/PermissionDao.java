package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.model.survey.activity.permission.Permission;

public interface PermissionDao {
  
  List<Permission> find();

}
