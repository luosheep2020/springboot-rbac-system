package com.rbac.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_operation_log")
@Data
public class OperationLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String method;

  private String uri;

  private String params;

  private Integer status;

  private LocalDateTime createTime;
}
