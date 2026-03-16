package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sys_role_permission")
public class RolePermission {

  @EmbeddedId
  private RolePermissionId id = new RolePermissionId();

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private Role role;

  @ManyToOne
  @MapsId("permissionId")
  @JoinColumn(name = "permission_id")
  private Permission permission;

  private Integer deleted = 0;
}
