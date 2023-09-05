package com.furryfriendsfinder.salmonCookiesD7.repositores;


import com.furryfriendsfinder.salmonCookiesD7.models.SalmonCookiesStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalmonCookiesStoreRepository extends JpaRepository<SalmonCookiesStore,Long> {

    String findByName(String name);
}
