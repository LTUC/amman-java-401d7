package com.LTUC.springSecurityForTheFirstTime.repositories;

import com.LTUC.springSecurityForTheFirstTime.models.DinosaursLover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DinosaursLoverRepository extends JpaRepository<DinosaursLover,Long> {
    DinosaursLover findByUsername(String username);
}
