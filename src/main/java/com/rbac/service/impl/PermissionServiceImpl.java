package com.rbac.service.impl;

import com.rbac.entity.Permission;
import com.rbac.entity.RolePermission;
import com.rbac.exception.BusinessException;
import com.rbac.exception.ErrorCode;
import com.rbac.repository.PermissionRepository;
import com.rbac.repository.RolePermissionRepository;
import com.rbac.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;

  @Override
  public List<Permission> findAll() {
    return permissionRepository.findAllByDeleted(0);
  }

  @Override
  public Permission findById(Long id) {
    return permissionRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_NOT_FOUND));
  }

  @Override
  @Transactional
  public Permission createPermission(Permission permission) {
    boolean exists = permissionRepository.existsByCodeAndDeleted(permission.getCode(), 0);
    if (exists) {
      throw new BusinessException(ErrorCode.PERMISSION_CODE_ALREADY_EXISTS);
    }

    Permission newPermission = new Permission();
    newPermission.setName(permission.getName());
    newPermission.setCode(permission.getCode());
    newPermission.setType(permission.getType());
    newPermission.setDeleted(0);

    return permissionRepository.save(newPermission);
  }

  @Override
  @Transactional
  public Permission updatePermission(Long id, Permission permission) {
    Permission dbPermission = permissionRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_NOT_FOUND));

    if (permission.getCode() != null && !permission.getCode().isBlank()) {
      boolean exists = permissionRepository.existsByCodeAndDeletedAndIdNot(
        permission.getCode(), 0, id
      );
      if (exists) {
        throw new BusinessException(ErrorCode.PERMISSION_CODE_ALREADY_EXISTS);
      }
      dbPermission.setCode(permission.getCode());
    }

    if (permission.getName() != null && !permission.getName().isBlank()) {
      dbPermission.setName(permission.getName());
    }

    if (permission.getType() != null && !permission.getType().isBlank()) {
      dbPermission.setType(permission.getType());
    }

    return permissionRepository.save(dbPermission);
  }

  @Override
  @Transactional
  public void deletePermission(Long id) {
    Permission permission = permissionRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_NOT_FOUND));

    permission.setDeleted(1);
    permissionRepository.save(permission);

    List<RolePermission> rolePermissions = rolePermissionRepository.findByPermission_IdAndDeleted(id, 0);
    for (RolePermission rolePermission : rolePermissions) {
      rolePermission.setDeleted(1);
    }
    rolePermissionRepository.saveAll(rolePermissions);
  }
}
