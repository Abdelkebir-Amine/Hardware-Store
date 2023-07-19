package com.polytec.onlineStore.service;

import com.polytec.onlineStore.dao.ClientRepository;
import com.polytec.onlineStore.entities.Client;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements IServiceClient{

    ClientRepository clientRepository;

    @Override
    public void addClient(Client c){
        clientRepository.save(c);
    }

    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Integer id){
        clientRepository.deleteById(id);
    }

    @Override
    public Page<Client> findByNomContains(String motCle, Pageable pageable){
        return clientRepository.findByNomContains(motCle,pageable);
    }

    @Override
    public Client getClient (Integer id){
        return clientRepository.findById(id).orElse(null);
    }
}
