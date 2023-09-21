package com.LTUC.springSecurityIndustryWorld.repositories;

import com.LTUC.springSecurityIndustryWorld.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    @Query(value = "SELECT * FROM Roles r WHERE r.title = :title", nativeQuery = true)
    Optional<RoleEntity> findRoleEntityByTitle(String title);
}
