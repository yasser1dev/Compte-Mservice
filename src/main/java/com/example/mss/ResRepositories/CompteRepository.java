package com.example.mss.ResRepositories;

import com.example.mss.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long> {
}
