package com.example.mss;


import com.example.mss.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENTS-SERVICE")
public interface RestRepoClient {
    @GetMapping(path="clients/{id}")
    Client getClientById(@PathVariable(value = "id") Long id);
}
