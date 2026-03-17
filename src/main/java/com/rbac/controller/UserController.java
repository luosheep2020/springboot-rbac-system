package com.rbac.controller;

import com.rbac.common.Response;
import com.rbac.dto.AssignRoleRequest;
import com.rbac.dto.CreateUserRequest;
import com.rbac.entity.Role;
import com.rbac.entity.User;
import com.rbac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @GetMapping("/list")
  @PreAuthorize("hasAnyAuthority('user:list')")
  public Response<List<User>> getAllUsers() {
    return Response.success(userService.findAll());
  }

  @GetMapping("/{id}")
  public Response<User> detail(@PathVariable Long id) {
    return Response.success(userService.findById(id));
  }

  @PostMapping("/create")
  public Response<User> create(@RequestBody CreateUserRequest request) {
    return Response.success(userService.createUser(request));
  }

  @PutMapping("/{id}")
  public Response<User> update(@PathVariable Long id,
                               @RequestBody CreateUserRequest request) {
    return Response.success(userService.updateUser(id, request));
  }

  @DeleteMapping("/{id}")
  public Response<Void> delete(@PathVariable Long id) {
    userService.deleteUser(id);
    return Response.success(null);
  }

  @PostMapping("/{id}/roles")
  public Response<Void> assignRoles(@PathVariable Long id,
                                    @RequestBody AssignRoleRequest request) {
    userService.assignRolesToUser(id, request.getRoleIds());
    return Response.success(null);
  }

  @GetMapping("/{id}/roles")
  public Response<List<Role>> getRoles(@PathVariable Long id) {
    return Response.success(userService.getRolesByUserId(id));
  }
}
