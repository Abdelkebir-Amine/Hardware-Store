package com.polytec.onlineStore.service;

import com.polytec.onlineStore.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IServiceProduit {
    void addProduit(Produit p, MultipartFile mf) throws IOException;
    void deleteProduit (Integer id);
    List<Produit> getAllProduits();
    Page<Produit> findByNomContains(String motCle, Pageable pageable);
    Produit getProduit(Integer id);
    List<Produit> findAllById(List<Integer> produitIds);
}
