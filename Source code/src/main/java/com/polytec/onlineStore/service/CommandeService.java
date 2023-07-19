package com.polytec.onlineStore.service;

import com.polytec.onlineStore.dao.CommandeRepository;
import com.polytec.onlineStore.entities.Commande;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeService implements IServiceCommande {
    CommandeRepository commandeRepository;

    @Override
    public void addCommande(Commande commande){
        commandeRepository.save(commande);
    }

    @Override
    public List<Commande> getAllCommandes(){
        return commandeRepository.findAll();
    }

    @Override
    public Page<Commande> findCmdByClientName(String motCle, Pageable pageable){
        return commandeRepository.findCmdByClientName(motCle,pageable);
    }

    @Override
    public void deleteCommande(Integer id){
        commandeRepository.deleteById(id);
    }

    @Override
    public Commande getCommande(Integer id){
        return commandeRepository.findById(id).orElse(null);
    }
}
