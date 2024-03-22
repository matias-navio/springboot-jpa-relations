package com.matias.springboot.jpa.springbootjparelations.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.springboot.jpa.springbootjparelations.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

}
