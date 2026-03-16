package com.rbac.service.impl;

import com.rbac.entity.Permission;
import com.rbac.entity.Role;
import com.rbac.entity.RolePermission;
import com.rbac.entity.RolePermissionId;
import com.rbac.exception.BusinessException;
import com.rbac.exception.ErrorCode;
import com.rbac.repository.PermissionRepository;
import com.rbac.repository.RolePermissionRepository;
import com.rbac.repository.RoleRepository;
import com.rbac.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;

  @Override
  public List<Role> findAll() {
    return roleRepository.findAllByDeleted(0);
  }

  @Override
  public Role findById(Long id) {
    return roleRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
  }

  @Override
  @Transactional
  public Role createRole(Role role) {
    boolean exists = roleRepository.existsByCodeAndDeleted(role.getCode(), 0);
    if (exists) {
      throw new BusinessException(ErrorCode.ROLE_CODE_ALREADY_EXISTS);
    }

    Role newRole = new Role();
    newRole.setName(role.getName());
    newRole.setCode(role.getCode());
    newRole.setDescription(role.getDescription());
    newRole.setDeleted(0);

    return roleRepository.save(newRole);
  }

  @Override
  @Transactional
  public Role updateRole(Long id, Role role) {
    Role dbRole = roleRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

    if (role.getCode() != null && !role.getCode().isBlank()) {
      boolean exists = roleRepository.existsByCodeAndDeletedAndIdNot(role.getCode(), 0, id);
      if (exists) {
        throw new BusinessException(ErrorCode.ROLE_CODE_ALREADY_EXISTS);
      }
      dbRole.setCode(role.getCode());
    }

    if (role.getName() != null && !role.getName().isBlank()) {
      dbRole.setName(role.getName());
    }

    if (role.getDescription() != null) {
      dbRole.setDescription(role.getDescription());
    }

    return roleRepository.save(dbRole);
  }

  @Override
  @Transactional
  public void deleteRole(Long id) {
    Role role = roleRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

    role.setDeleted(1);
    roleRepository.save(role);

    List<RolePermission> rolePermissions = rolePermissionRepository.findByRole_IdAndDeleted(id, 0);
    for (RolePermission rolePermission : rolePermissions) {
      rolePermission.setDeleted(1);
    }
    rolePermissionRepository.saveAll(rolePermissions);
  }

  @Override
  @Transactional
  public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
    Role role = roleRepository.findByIdAndDeleted(roleId, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

    if (permissionIds == null || permissionIds.isEmpty()) {
      throw new BusinessException(ErrorCode.BAD_REQUEST.getCode(), "permissionIds cannot be empty");
    }

    List<Permission> permissions = permissionRepository.findAllByIdInAndDeleted(permissionIds, 0);
    if (permissions.size() != permissionIds.size()) {
      throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
    }

    List<RolePermission> oldRelations = rolePermissionRepository.findByRole_IdAndDeleted(roleId, 0);
    for (RolePermission oldRelation : oldRelations) {
      oldRelation.setDeleted(1);
    }
    rolePermissionRepository.saveAll(oldRelations);

    Set<Long> uniquePermissionIds = new LinkedHashSet<>(permissionIds);
    List<RolePermission> newRelations = new ArrayList<>();

    for (Long permissionId : uniquePermissionIds) {
      Permission permission = permissions.stream()
        .filter(p -> p.getId().equals(permissionId))
        .findFirst()
        .orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_NOT_FOUND));

      RolePermission rolePermission = new RolePermission();
      rolePermission.setId(new RolePermissionId(roleId, permissionId));
      rolePermission.setRole(role);
      rolePermission.setPermission(permission);
      rolePermission.setDeleted(0);

      newRelations.add(rolePermission);
    }

    rolePermissionRepository.saveAll(newRelations);
  }

  @Override
  public List<Permission> getPermissionsByRoleId(Long roleId) {
    roleRepository.findByIdAndDeleted(roleId, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

    List<RolePermission> rolePermissions = rolePermissionRepository.findByRole_IdAndDeleted(roleId, 0);
    return rolePermissions.stream()
      .map(RolePermission::getPermission)
      .toList();
  }
}
