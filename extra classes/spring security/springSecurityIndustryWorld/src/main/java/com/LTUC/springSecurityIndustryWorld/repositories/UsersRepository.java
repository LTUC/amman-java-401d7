package com.LTUC.springSecurityIndustryWorld.repositories;

import com.LTUC.springSecurityIndustryWorld.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {

    Optional<UsersEntity> findByUsername(String username);
}
