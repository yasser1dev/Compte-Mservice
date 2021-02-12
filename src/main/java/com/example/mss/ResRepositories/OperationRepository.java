package com.example.mss.ResRepositories;

import com.example.mss.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;

@RepositoryRestResource
public interface OperationRepository extends JpaRepository<Operation,Long> {
    PagedModel<Operation> findByCompteCode(Long code);
}
