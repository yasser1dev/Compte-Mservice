package com.example.mss;

import com.example.mss.ResRepositories.CompteRepository;
import com.example.mss.ResRepositories.OperationRepository;
import com.example.mss.entity.*;
import com.example.mss.model.Client;
import com.example.mss.model.OperationKafka;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableKafka
@EnableFeignClients
public class CompteMssApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteMssApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository, OperationRepository operationRepository, RestRepoClient restRepoClient,
                            RepositoryRestConfiguration repositoryRestConfigurer, KafkaTemplate<String, OperationKafka> kafkaTemplate){
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

            final String topic="operations";
            produceOperation(kafkaTemplate,topic);


        };
    }
    public void produceOperation(KafkaTemplate<String, OperationKafka> kafkaTemplate, String topic){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
            OperationKafka operation=new OperationKafka((int)(1+Math.random()*10000),new Date()
                    ,1+Math.random()*1000,
                    TypeOperation.values()[(int)(Math.random()*2)],(int)(Math.random()*6));
            System.out.println(operation);
            kafkaTemplate.send(topic,"key : "+operation.getNumero(),operation);
            System.out.println("-------------------------------------------");
        },1000,1000, TimeUnit.MILLISECONDS);

    }
}
