package com.example.mss.model;

import com.example.mss.entity.Compte;
import com.example.mss.entity.TypeOperation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data @AllArgsConstructor
public class OperationKafka {
    private Integer numero;
    private Date dateOperation;
    private double montant;
    private TypeOperation typeOperation;
    private long  id_compte;
}
