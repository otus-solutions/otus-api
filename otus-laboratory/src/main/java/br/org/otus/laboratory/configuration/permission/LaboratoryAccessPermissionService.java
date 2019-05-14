package br.org.otus.laboratory.configuration.permission;

import java.util.List;

public interface LaboratoryAccessPermissionService {

  void create(LaboratoryAccessPermission laboratoryAccessPermissionDto);

  void update(LaboratoryAccessPermission laboratoryAccessPermissionDto);

  List<LaboratoryAccessPermission> list();

}
