package com.matias.springboot.jpa.springbootjparelations.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matias.springboot.jpa.springbootjparelations.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{

    @Query("select c from Client c left join fetch c.invoices where c.id=?1")
    Optional<Client> findOne(Long id);
}
