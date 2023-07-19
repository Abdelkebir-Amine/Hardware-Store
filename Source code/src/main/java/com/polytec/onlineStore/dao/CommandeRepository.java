package com.polytec.onlineStore.dao;

import com.polytec.onlineStore.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    //Requette jpql
    @Query("select c from Commande c where c.client.nom like %:motCle%")
    public Page<Commande> findCmdByClientName (@Param("motCle") String motCle, Pageable pageable);
}