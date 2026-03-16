package com.rbac.repository;

import com.rbac.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

  List<Permission> findAllByDeleted(Integer deleted);

  Optional<Permission> findByIdAndDeleted(Long id, Integer deleted);

  boolean existsByCodeAndDeleted(String code, Integer deleted);

  boolean existsByCodeAndDeletedAndIdNot(String code, Integer deleted, Long id);

  List<Permission> findAllByIdInAndDeleted(List<Long> ids, Integer deleted);
}
