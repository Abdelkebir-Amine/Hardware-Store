package com.polytec.onlineStore.service;

import com.polytec.onlineStore.entities.Commande;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceCommande {
    void addCommande(Commande commande);
    void deleteCommande (Integer id);
    List<Commande> getAllCommandes();
    Page<Commande> findCmdByClientName(String motCle, Pageable pageable);
    Commande getCommande(Integer id);
}
