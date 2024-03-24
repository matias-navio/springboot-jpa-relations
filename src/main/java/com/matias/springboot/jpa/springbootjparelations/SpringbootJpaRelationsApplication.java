package com.matias.springboot.jpa.springbootjparelations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.matias.springboot.jpa.springbootjparelations.entities.Address;
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

		oneToManyFindByIdClient();
		
	}	

	@Transactional
	public void oneToManyFindByIdClient(){
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresentOrElse(c -> {
			c.setAddresses(Arrays.asList(
				new Address("Salta", 211),
				new Address("Viamonte", 2211)
			));

			Client clientDb = clientRepository.save(c);
			System.out.println("====== Buscamos un cliente, y le asignamos 2 direcciones ======");
			System.out.println(clientDb);
			
		}, () -> System.out.println("El cliente no se encuentra en la base de datos"));
	}

	@Transactional
	public void oneToManyCreateClient(){
		Client client = new Client("Exequiel", "Carrizo");
		
		Address address1 = new Address("Salta", 211);
		Address address2 = new Address("Viamonte", 2211);
		
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);
		System.out.println("====== Cliente nuevo, con una lista de direcciones ======");
		System.out.println(client);
	}

	@Transactional
	public void manyToOneCreateClient(){ 

		// creamos cliente nuevo y los asignamos a una factura
		Client client = new Client("Santiago", "Federisi");
		clientRepository.save(client);
		System.out.println(client);

		Invoice invoice = new Invoice("Compras deportivas", 5000L);
		invoice.setClient(client);
		
		Invoice invoiceDb = invoiceRepository.save(invoice);
		System.out.println(invoiceDb);

	}

	@Transactional
	public void manyToOneFindByIdClient(){ 

		// buscamos un cliente y lo asignamos a una factura
		Optional<Client> optionalClient = clientRepository.findById(1L);

		Invoice invoice = new Invoice("Compras de oficina", 1000L);

		optionalClient.ifPresentOrElse(c -> invoice.setClient(c), () -> System.out.println("El cliente no se encuentra en la base de datos"));
		
		Invoice invoiceDb = invoiceRepository.save(invoice);
		System.out.println(invoiceDb);
	}

	
}
