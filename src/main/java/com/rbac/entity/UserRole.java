package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sys_user_role")
public class UserRole {

  @EmbeddedId
  private UserRoleId id = new UserRoleId();

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private Role role;

  private Integer deleted = 0;
}
