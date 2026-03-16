package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sys_permission")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String code;
  private String type;
  private Integer deleted = 0;

  @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
  private List<RolePermission> rolePermissions;
}
