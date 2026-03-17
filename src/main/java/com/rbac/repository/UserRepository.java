package com.rbac.repository;

import com.rbac.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByDeleted(Integer deleted);

  Optional<User> findByIdAndDeleted(Long id, Integer deleted);

  Optional<User> findByUsernameAndDeletedAndEnabled(String username, Integer deleted, Integer enabled);

  boolean existsByUsernameAndDeleted(String username, Integer deleted);

  boolean existsByUsernameAndDeletedAndIdNot(String username, Integer deleted, Long id);

  @EntityGraph(attributePaths =
    {"userRoles",
      "userRoles.role",
      "userRoles.role.rolePermissions",
      "userRoles.role.rolePermissions.permission"})
  Optional<User> findWithRolesAndPermissionsByUsernameAndDeletedAndEnabled(String username, Integer deleted, Integer enabled);
}
