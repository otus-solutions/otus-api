package br.org.otus.laboratory.permission;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.configuration.permission.LaboratoryAccessPermission;
import br.org.otus.laboratory.configuration.permission.LaboratoryAccessPermissionService;

public class LaboratoryAccessPermissionFacade {

  @Inject
  private LaboratoryAccessPermissionService laboratoryAccessPermissionService;

  public void create(LaboratoryAccessPermission permission) {
    laboratoryAccessPermissionService.create(permission);
  }

  public void update(LaboratoryAccessPermission permission) {
    laboratoryAccessPermissionService.update(permission);
  }

  public List<LaboratoryAccessPermission> getAll() {
    return laboratoryAccessPermissionService.list();
  }

}
