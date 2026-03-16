package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "sys_role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String code;
  private String description;
  private Integer deleted = 0;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private List<UserRole> userRoles;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private List<RolePermission> rolePermissions;
}
