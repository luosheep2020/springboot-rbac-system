package com.rbac.service;
import com.rbac.dto.CreateUserRequest;
import com.rbac.entity.Role;
import com.rbac.entity.User;
import java.util.List;



public interface UserService {
  List<User> findAll();

  User findById(Long id);

  User createUser(CreateUserRequest request);

  User updateUser(Long id, CreateUserRequest request);

  void deleteUser(Long id);

  void assignRolesToUser(Long userId, List<Long> roleIds);

  List<Role> getRolesByUserId(Long userId);
}
