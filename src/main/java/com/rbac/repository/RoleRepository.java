package com.rbac.repository;

import com.rbac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByIdAndDeleted(Long id, Integer deleted);
  List<Role> findAllByIdInAndDeleted(List<Long> ids, Integer deleted);
  List<Role> findAllByDeleted(Integer deleted);
  boolean existsByCodeAndDeleted(String code, Integer deleted);
  boolean existsByCodeAndDeletedAndIdNot(String code, Integer deleted, Long id);

}
