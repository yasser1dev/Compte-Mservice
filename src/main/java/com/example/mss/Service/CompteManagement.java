package com.example.mss.Service;

import com.example.mss.entity.Compte;
import com.example.mss.entity.Operation;
import org.springframework.hateoas.PagedModel;

public interface CompteManagement {
    public void addCompte(Compte compte);
    public boolean versementCompte(double montant,Long codeCompte);
    public boolean retraitCompte(double montant,Long codeCompte);
    public boolean virementCompte(double montant,Long codeCompte1,Long codeCompte2);
    public PagedModel<Operation> getCompteOperation(Long codeCompte);
    public Compte getFullInformationCompte(Long codeCompte);
    public void changeStateCompte(Long codeCompte,int stateCode);
}
