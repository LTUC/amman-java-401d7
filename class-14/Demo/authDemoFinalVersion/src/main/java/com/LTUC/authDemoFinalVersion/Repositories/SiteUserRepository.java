package com.LTUC.authDemoFinalVersion.Repositories;

import com.LTUC.authDemoFinalVersion.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SiteUserRepository extends JpaRepository<SiteUser,Long> {

    //@Query("select username from SiteUser where SiteUser.username=username")
    SiteUser findByUsername(String username);
}
