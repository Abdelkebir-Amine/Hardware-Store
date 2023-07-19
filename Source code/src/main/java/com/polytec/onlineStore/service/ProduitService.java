package com.polytec.onlineStore.service;

import com.polytec.onlineStore.dao.ProduitRepository;
import com.polytec.onlineStore.entities.Produit;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
public class ProduitService implements IServiceProduit{

    ProduitRepository produitRepository;

    @Override
    public void addProduit(Produit p, MultipartFile mf) throws IOException{
        if(!mf.isEmpty())
            p.setNomImage(saveImage(mf));
        produitRepository.save(p);
    }

    @Override
    public List<Produit> getAllProduits(){
        return produitRepository.findAll();
    }

    @Override
    public void deleteProduit(Integer id){
        produitRepository.deleteById(id);
    }

    @Override
    public Page<Produit> findByNomContains(String motCle, Pageable pageable){
        return produitRepository.findByNomContains(motCle,pageable);
    }

    @Override
    public Produit getProduit (Integer id){
        return produitRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produit> findAllById (List<Integer> produitIds){
        return produitRepository.findAllById(produitIds);
    }

    private String saveImage (MultipartFile mf) throws IOException{
        String originalName = mf.getOriginalFilename();
        String tab[] = originalName.split("\\.");
        String newName = tab[0]+System.currentTimeMillis()+"."+tab[1];
        String pp = System.getProperty("user.dir")+"/src/main/resources/static/photos";
        Path p = Paths.get(pp,newName);
        Files.write(p,mf.getBytes());
        return newName;
    }
}
