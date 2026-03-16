package com.rbac.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignRoleRequest {

  private List<Long> roleIds;
}
