package com.rbac.service.impl;

import com.rbac.dto.CreateUserRequest;
import com.rbac.entity.Role;
import com.rbac.entity.User;
import com.rbac.entity.UserRole;
import com.rbac.entity.UserRoleId;
import com.rbac.exception.BusinessException;
import com.rbac.exception.ErrorCode;
import com.rbac.repository.RoleRepository;
import com.rbac.repository.UserRepository;
import com.rbac.repository.UserRoleRepository;
import com.rbac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserRoleRepository userRoleRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAllByDeleted(0);
  }

  @Override
  public User findById(Long id) {
    return userRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
  }

  @Override
  @Transactional
  public User createUser(CreateUserRequest request) {
    boolean exists = userRepository.existsByUsernameAndDeleted(request.getUsername(), 0);
    if (exists) {
      throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }

    User user = new User()
      .setUsername(request.getUsername())
      .setPassword(request.getPassword())
      .setEnabled(request.getEnabled() == null ? 1 : request.getEnabled())
      .setDeleted(0);

    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User updateUser(Long id, CreateUserRequest request) {
    User user = userRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    if (request.getUsername() != null && !request.getUsername().isBlank()) {
      boolean exists = userRepository.existsByUsernameAndDeletedAndIdNot(
        request.getUsername(), 0, id
      );
      if (exists) {
        throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
      }
      user.setUsername(request.getUsername());
    }

    if (request.getPassword() != null && !request.getPassword().isBlank()) {
      user.setPassword(request.getPassword());
    }

    if (request.getEnabled() != null) {
      user.setEnabled(request.getEnabled());
    }

    return userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findByIdAndDeleted(id, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    user.setDeleted(1);
    userRepository.save(user);

    List<UserRole> userRoles = userRoleRepository.findByUser_IdAndDeleted(id, 0);
    for (UserRole userRole : userRoles) {
      userRole.setDeleted(1);
    }
    userRoleRepository.saveAll(userRoles);
  }

  @Override
  @Transactional
  public void assignRolesToUser(Long userId, List<Long> roleIds) {
    User user = userRepository.findByIdAndDeleted(userId, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    if (roleIds == null || roleIds.isEmpty()) {
      throw new BusinessException(ErrorCode.BAD_REQUEST.getCode(), "roleIds cannot be empty");
    }

    List<Role> roles = roleRepository.findAllByIdInAndDeleted(roleIds, 0);
    if (roles.size() != roleIds.size()) {
      throw new BusinessException(ErrorCode.NOT_FOUND.getCode(), "some roles do not exist");
    }

    // 先逻辑删除旧关系
    List<UserRole> oldUserRoles = userRoleRepository.findByUser_IdAndDeleted(userId, 0);
    for (UserRole oldUserRole : oldUserRoles) {
      oldUserRole.setDeleted(1);
    }
    userRoleRepository.saveAll(oldUserRoles);

    // 去重，防止重复 roleId
    Set<Long> uniqueRoleIds = new LinkedHashSet<>(roleIds);
    List<UserRole> newUserRoles = new ArrayList<>();

    for (Long roleId : uniqueRoleIds) {
      UserRole userRole = new UserRole();
      userRole.setId(new UserRoleId(userId, roleId));
      userRole.setUser(user);
      Role role = roles.stream()
        .filter(r -> r.getId().equals(roleId))
        .findFirst()
        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND.getCode(), "role not found"));
      userRole.setRole(role);
      userRole.setDeleted(0);
      newUserRoles.add(userRole);
    }

    userRoleRepository.saveAll(newUserRoles);
  }

  @Override
  public List<Role> getRolesByUserId(Long userId) {
    userRepository.findByIdAndDeleted(userId, 0)
      .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    List<UserRole> userRoles = userRoleRepository.findByUser_IdAndDeleted(userId, 0);
    return userRoles.stream()
      .map(UserRole::getRole)
      .toList();
  }
}
