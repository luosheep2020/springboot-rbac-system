package com.rbac.service;

import com.rbac.entity.Permission;
import com.rbac.entity.Role;

import java.util.List;

public interface RoleService {

  List<Role> findAll();

  Role findById(Long id);

  Role createRole(Role role);

  Role updateRole(Long id, Role role);

  void deleteRole(Long id);

  void assignPermissionsToRole(Long roleId, List<Long> permissionIds);

  List<Permission> getPermissionsByRoleId(Long roleId);
}
