package com.example.mss.entity;


import com.example.mss.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private double sold;
    private Date dateCreation;
    private long client_id;
    @Transient
    private Client client;
    @Enumerated(EnumType.ORDINAL)
    private TypeCompte typeCompte;
    @Enumerated(EnumType.ORDINAL)
    private Etat etat;
    @OneToMany(mappedBy = "compte")
    private Collection<Operation> operations;

}
