package br.org.otus.laboratory.configuration.permission;

import java.util.List;

public interface LaboratoryAccessPermissionDao {

  List<LaboratoryAccessPermission> find();

  void persist(LaboratoryAccessPermission laboratoryAccessPermission);

  void update(LaboratoryAccessPermission laboratoryAccessPermission);

}
