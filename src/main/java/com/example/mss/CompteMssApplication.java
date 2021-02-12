package com.example.mss;

import com.example.mss.ResRepositories.CompteRepository;
import com.example.mss.ResRepositories.OperationRepository;
import com.example.mss.entity.*;
import com.example.mss.model.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class CompteMssApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteMssApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository, OperationRepository operationRepository,RestRepoClient restRepoClient,
                            RepositoryRestConfiguration repositoryRestConfigurer){
        return args -> {
            repositoryRestConfigurer.exposeIdsFor(Operation.class,Compte.class);
            Client client=restRepoClient.getClientById(1L);
            Compte compte=compteRepository.save(new Compte(null,10000.0,new Date(),client.getCode(),client,
                    TypeCompte.COURANT,Etat.ACTIVE,null));
            Collection<Operation> operations=new ArrayList<>();
            operations.add(new Operation(null,new Date(),500, TypeOperation.CREDIT,compte));
            operations.add(new Operation(null,new Date(),500, TypeOperation.DEBIT,compte));

            Collection<Operation> operationsCompte=operationRepository.saveAll(operations);

            compte.setOperations(operationsCompte);
            compteRepository.save(compte);

        };
    }
}
