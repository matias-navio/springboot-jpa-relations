package com.matias.springboot.jpa.springbootjparelations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matias.springboot.jpa.springbootjparelations.entities.Client;
import com.matias.springboot.jpa.springbootjparelations.entities.Invoice;
import com.matias.springboot.jpa.springbootjparelations.repositories.ClientRepository;
import com.matias.springboot.jpa.springbootjparelations.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationsApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		manyToOne();
	}	

	public void manyToOne(){ 

		// buscamos un cliente y lo asignamos a una factura
		Client client = clientRepository.findById(1L).get();

		Invoice invoice = new Invoice("Compras de oficina", 1000L);

		invoice.setClient(client);		
		
		Invoice invoiceDb = invoiceRepository.save(invoice);
		System.out.println(invoiceDb);

		// creamos cliente nuevo y los asignamos a una factura
		Client client2 = new Client("Matias", "Navio");
		clientRepository.save(client2);

		Invoice invoice2 = new Invoice("Compras deportivas", 5000L);
		invoice2.setClient(client2);
		Invoice invoiceDb2 = invoiceRepository.save(invoice2);
		System.out.println(invoiceDb2);
	}
}
