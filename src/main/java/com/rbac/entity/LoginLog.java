package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_login_log")
@Data
public class LoginLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String ip;

  private Integer status;

  private String message;

  private LocalDateTime createTime;
}
