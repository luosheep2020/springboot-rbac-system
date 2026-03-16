package com.rbac.repository;

import com.rbac.entity.RolePermission;
import com.rbac.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {


  List<RolePermission> findByPermission_IdAndDeleted(Long permissionId, Integer deleted);
  List<RolePermission> findByRole_IdAndDeleted(Long roleId, Integer deleted);
}
