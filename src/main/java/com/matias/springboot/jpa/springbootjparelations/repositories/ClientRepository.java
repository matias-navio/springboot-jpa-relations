package com.matias.springboot.jpa.springbootjparelations.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.springboot.jpa.springbootjparelations.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{

}
