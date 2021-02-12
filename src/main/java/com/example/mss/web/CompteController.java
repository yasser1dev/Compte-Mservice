package com.example.mss.web;


import com.example.mss.Service.CompteManagement;
import com.example.mss.entity.Compte;
import com.example.mss.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompteController {
    @Autowired
    CompteManagement compteManagement;


    @PostMapping("addCompte")
    public void addCompte(@RequestBody Compte compte){
        compteManagement.addCompte(compte);
    }

    @GetMapping("/versementCompte/{id}/{montant}")
    public void versementCompte(@PathVariable("id") Long id,@PathVariable("montant") double montant){
        compteManagement.versementCompte(montant,id);
    }
    @GetMapping("/retraitCompte/{id}/{montant}")
    public void retraitCompte(@PathVariable("id") Long id,@PathVariable("montant") double montant){
        compteManagement.retraitCompte(montant,id);
    }


    @GetMapping("/fullCompte/{id}")
    public Compte getFullcompte(@PathVariable("id") Long id){
        return compteManagement.getFullInformationCompte(id);
    }

    @PostMapping("/virementCompte/{id1}/{montant}/{id2}")
    public void virementCompte(@PathVariable("id1") Long idSender,@PathVariable("montant") double montant,
                               @PathVariable("id2") Long idReciever){
        compteManagement.virementCompte(montant,idSender,idReciever);
    }


    @GetMapping("/compteOperations/{id}")
    public PagedModel<Operation> getCompteOperation(@PathVariable("id") Long codeCompte){
        return compteManagement.getCompteOperation(codeCompte);
    }

    @GetMapping("/changeCompteState/{id}/{state}")
    public void changeStateCompte(@PathVariable("id") Long id,@PathVariable("state") int state){
        compteManagement.changeStateCompte(id,state);
    }

}
