package com.rbac.controller;

import com.rbac.common.Response;
import com.rbac.dto.AssignPermissionRequest;
import com.rbac.entity.Permission;
import com.rbac.entity.Role;
import com.rbac.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  /**
   * 查询角色列表
   */
  @GetMapping("/list")
  public Response<List<Role>> list() {
    return Response.success(roleService.findAll());
  }

  /**
   * 根据ID查询角色详情
   */
  @GetMapping("/{id}")
  public Response<Role> detail(@PathVariable Long id) {
    return Response.success(roleService.findById(id));
  }

  /**
   * 创建角色
   */
  @PostMapping("/create")
  public Response<Role> create(@RequestBody Role role) {
    return Response.success(roleService.createRole(role));
  }

  /**
   * 更新角色
   */
  @PutMapping("/{id}")
  public Response<Role> update(@PathVariable Long id,
                               @RequestBody Role role) {
    return Response.success(roleService.updateRole(id, role));
  }

  /**
   * 删除角色（逻辑删除）
   */
  @DeleteMapping("/{id}")
  public Response<Void> delete(@PathVariable Long id) {
    roleService.deleteRole(id);
    return Response.<Void>success(null);
  }

  /**
   * 给角色分配权限
   */
  @PostMapping("/{id}/permissions")
  public Response<Void> assignPermissions(@PathVariable Long id,
                                          @RequestBody AssignPermissionRequest request) {
    roleService.assignPermissionsToRole(id, request.getPermissionIds());
    return Response.<Void>success(null);
  }

  /**
   * 查询角色拥有的权限
   */
  @GetMapping("/{id}/permissions")
  public Response<List<Permission>> getPermissions(@PathVariable Long id) {
    return Response.success(roleService.getPermissionsByRoleId(id));
  }
}
