package com.rbac.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionRequest {

  private List<Long> permissionIds;
}
