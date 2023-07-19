package com.polytec.onlineStore.entities;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Future(message = "Veuillez entrer une date valide, supérieure à la date d'aujourd'hui!")
    private Date date;
    @ManyToOne
    private Client client;
    @ManyToMany
    private List<Produit> produit;

}
