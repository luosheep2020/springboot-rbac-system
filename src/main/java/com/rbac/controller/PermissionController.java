package com.rbac.controller;

import com.rbac.common.Response;
import com.rbac.entity.Permission;
import com.rbac.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;

  /**
   * 查询权限列表
   */
  @GetMapping("/list")
  public Response<List<Permission>> list() {
    return Response.success(permissionService.findAll());
  }

  /**
   * 根据ID查询权限详情
   */
  @GetMapping("/{id}")
  public Response<Permission> detail(@PathVariable Long id) {
    return Response.success(permissionService.findById(id));
  }

  /**
   * 创建权限
   */
  @PostMapping("/create")
  public Response<Permission> create(@RequestBody Permission permission) {
    return Response.success(permissionService.createPermission(permission));
  }

  /**
   * 更新权限
   */
  @PutMapping("/{id}")
  public Response<Permission> update(@PathVariable Long id,
                                     @RequestBody Permission permission) {
    return Response.success(permissionService.updatePermission(id, permission));
  }

  /**
   * 删除权限（逻辑删除）
   */
  @DeleteMapping("/{id}")
  public Response<Void> delete(@PathVariable Long id) {
    permissionService.deletePermission(id);
    return Response.<Void>success(null);
  }
}
