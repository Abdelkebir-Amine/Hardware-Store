package com.polytec.onlineStore;

import com.polytec.onlineStore.dao.ClientRepository;
import com.polytec.onlineStore.dao.CommandeRepository;
import com.polytec.onlineStore.dao.ProduitRepository;
import com.polytec.onlineStore.entities.Client;
import com.polytec.onlineStore.entities.Commande;
import com.polytec.onlineStore.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class onlineStoreApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(onlineStoreApplication.class, args);
    }

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void run (String... args) throws Exception{
        System.out.println("\n**** Click here to try : http://localhost:8080/home ****\n");
    }


}
