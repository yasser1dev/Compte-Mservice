package com.example.mss.Service;

import com.example.mss.ResRepositories.CompteRepository;
import com.example.mss.ResRepositories.OperationRepository;
import com.example.mss.RestRepoClient;
import com.example.mss.entity.Compte;
import com.example.mss.entity.Etat;
import com.example.mss.entity.Operation;
import com.example.mss.entity.TypeOperation;
import com.example.mss.model.Client;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompteManagementImplementation implements CompteManagement {

    private CompteRepository compteRepository;
    private OperationRepository operationRepository;
    private RestRepoClient restRepoClient;

    public CompteManagementImplementation(CompteRepository compteRepository,
                                          OperationRepository operationRepository,
                                          RestRepoClient restRepoClient) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
        this.restRepoClient = restRepoClient;
    }


    @Override
    public void addCompte(Compte compte) {
        compteRepository.save(compte);
    }

    @Override
    public boolean versementCompte(double montant, Long codeCompte) {
        Compte compte=compteRepository.findById(codeCompte).get();
        if(compte.getEtat()==Etat.ACTIVE){
        compte.setSold(compte.getSold()+montant);
        Operation operation=new Operation(null,new Date(),montant,TypeOperation.CREDIT,compte);
        compte.getOperations().add(operation);
        operationRepository.save(operation);
        compteRepository.save(compte);
        return true;}
        return false;
    }

    @Override
    public boolean retraitCompte(double montant, Long codeCompte) {
        Compte compte=compteRepository.findById(codeCompte).get();
        if(compte.getEtat()==Etat.ACTIVE){double sold=compte.getSold();
        if(sold<montant) return false;
        compte.setSold(compte.getSold()-montant);
        Operation operation=new Operation(null,new Date(),montant,TypeOperation.DEBIT,compte);
        compte.getOperations().add(operation);
        operationRepository.save(operation);
        compteRepository.save(compte);
        return true;
        }
        return false;
    }

    @Override
    public boolean virementCompte(double montant, Long codeCompte1, Long codeCompte2) {
        Compte senderCompte=compteRepository.findById(codeCompte1).get();
        Compte recieverCompte=compteRepository.findById(codeCompte2).get();

        double soldSender=senderCompte.getSold();
        if(soldSender<montant) return false;
        senderCompte.setSold(senderCompte.getSold()-montant);
        recieverCompte.setSold(recieverCompte.getSold()+montant);
        Operation operation=new Operation(null,new Date(),montant,TypeOperation.DEBIT,senderCompte);
        senderCompte.getOperations().add(operation);

        operationRepository.save(operation);
        compteRepository.save(senderCompte);
        compteRepository.save(recieverCompte);
        return true;

    }

    @Override
    public PagedModel<Operation> getCompteOperation(Long codeCompte) {
        PagedModel<Operation> operations=operationRepository.findByCompteCode(codeCompte);
        return operations;
    }

    @Override
    public Compte getFullInformationCompte(Long codeCompte) {
        Compte compte=compteRepository.findById(codeCompte).get();
        Client client=restRepoClient.getClientById(compte.getClient_id());
        compte.setClient(client);
        return compte;
    }

    @Override
    public void changeStateCompte(Long codeCompte,int statCode) {
        Compte compte=compteRepository.findById(codeCompte).get();
        if(statCode==0) compte.setEtat(Etat.ACTIVE);
        else compte.setEtat(Etat.SUSPENDED);

        compteRepository.save(compte);
    }
}
