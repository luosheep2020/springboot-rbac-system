package com.rbac.service;

import com.rbac.entity.Permission;
import com.rbac.entity.RolePermission;
import com.rbac.entity.User;
import com.rbac.entity.UserRole;
import com.rbac.exception.BusinessException;
import com.rbac.exception.ErrorCode;
import com.rbac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

  private final UserRepository userRepository;

  public List<GrantedAuthority> loadUserAuthorities(String username) {
    User user = userRepository
      .findWithRolesAndPermissionsByUsernameAndDeletedAndEnabled(username, 0, 1)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    return user.getUserRoles().stream()
      .filter(userRole -> userRole.getDeleted() == 0)
      .map(UserRole::getRole)
      .filter(Objects::nonNull)
      .filter(role -> role.getDeleted() == 0)
      .flatMap(role -> role.getRolePermissions().stream())
      .filter(rolePermission -> rolePermission.getDeleted() == 0)
      .map(RolePermission::getPermission)
      .filter(Objects::nonNull)
      .filter(permission -> permission.getDeleted() == 0)
      .map(Permission::getCode)
      .filter(Objects::nonNull)
      .distinct()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }
}
