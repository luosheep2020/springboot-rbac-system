package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "sys_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  /**
   * 1 = enabled, 0 = disabled
   */
  @Column(name = "enabled")
  private Integer enabled;
  /**
   * 0 = not deleted, 1 = deleted
   */
  @Column(name = "deleted")
  private Integer deleted;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<UserRole> userRoles;
}
