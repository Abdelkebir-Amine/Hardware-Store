package com.polytec.onlineStore.dao;

import com.polytec.onlineStore.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    //Derived query
    public Page<Produit> findByNomContains (String motCle, Pageable pageable);
}
