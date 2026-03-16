package com.rbac.service;

import com.rbac.entity.Permission;

import java.util.List;

public interface PermissionService {

  List<Permission> findAll();

  Permission findById(Long id);

  Permission createPermission(Permission permission);

  Permission updatePermission(Long id, Permission permission);

  void deletePermission(Long id);
}
