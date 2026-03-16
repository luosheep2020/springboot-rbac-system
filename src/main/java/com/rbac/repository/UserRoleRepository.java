package com.rbac.repository;

import com.rbac.entity.UserRole;
import com.rbac.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

  List<UserRole> findByUser_IdAndDeleted(Long userId, Integer deleted);
}
