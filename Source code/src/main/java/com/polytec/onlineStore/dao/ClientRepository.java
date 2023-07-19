package com.polytec.onlineStore.dao;

import com.polytec.onlineStore.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    //Derived query
    public Page<Client> findByNomContains (String motCle, Pageable pageable);
}
