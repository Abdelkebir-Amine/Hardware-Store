package com.polytec.onlineStore.service;

import com.polytec.onlineStore.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IServiceClient {
    void addClient(Client p);
    void deleteClient (Integer id);
    List<Client> getAllClients();
    Page<Client> findByNomContains(String motCle, Pageable pageable);
    Client getClient(Integer id);
}
