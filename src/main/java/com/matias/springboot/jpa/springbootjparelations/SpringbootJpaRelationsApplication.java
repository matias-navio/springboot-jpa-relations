package com.matias.springboot.jpa.springbootjparelations;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.matias.springboot.jpa.springbootjparelations.entities.Address;
import com.matias.springboot.jpa.springbootjparelations.entities.Client;
import com.matias.springboot.jpa.springbootjparelations.entities.ClientDetails;
import com.matias.springboot.jpa.springbootjparelations.entities.Invoice;
import com.matias.springboot.jpa.springbootjparelations.repositories.ClientDetailsRepository;
import com.matias.springboot.jpa.springbootjparelations.repositories.ClientRepository;
import com.matias.springboot.jpa.springbootjparelations.repositories.InvoiceRepository;


@SpringBootApplication
public class SpringbootJpaRelationsApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		oneToOneBidirectionalFindById();		
	}	

	@Transactional
	public void oneToOneBidirectionalFindById(){
		
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresentOrElse(client -> {

			ClientDetails details = new ClientDetails(true, 5000);

			client.setClientDetails(details); 		 
			clientRepository.save(client);

			System.out.println(client);

		}, () -> System.out.println("El cliente no se encuentra en la base de datos!!"));

	}

	@Transactional
	public void oneToOneBidirectional(){
		
		Client client = new Client("Exequiel", "Carrizo");

		ClientDetails details = new ClientDetails(true, 5000);

		client.setClientDetails(details);
		details.setClient(client);
		 
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToOneFidById(){ 
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresentOrElse(client -> {

			ClientDetails details = new ClientDetails(false, 1500);
			clientDetailsRepository.save(details);

			client.setClientDetails(details);

			clientRepository.save(client);

			System.out.println("====== Buscamos un cliente y le asignamos detalles ======");
			System.out.println(client);
		}, () -> System.out.println("El cliente no se encuentra en la base de datos!"));
	}

	@Transactional
	public void oneToOneCreateClient(){

		ClientDetails clientDetail = new ClientDetails(true, 1000);
		clientDetailsRepository.save(clientDetail);

		Client client = new Client("Exeuiel", "Carrizo");
		client.setClientDetails(clientDetail);

		clientRepository.save(client);

		System.out.println("====== Cliente creado con sus detalles ======");
		System.out.println(client);
	}

	@Transactional
	public void removeInvoiceBidirectionalFindById(){
		// buscamos cliente
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		// método par aver si esta presente
		optionalClient.ifPresentOrElse(client -> {
			// creamos 2 facturas
			Invoice invoice1 = new Invoice("Factura de oficina", 2500L);
			Invoice invoice2 = new Invoice("Factura de deporte", 2200L);

			// se las agregamos al cliente
			client.addInvoice(invoice1)
					.addInvoice(invoice2);
			// lo guardamos en la base
			clientRepository.save(client);

			System.out.println("====== Cliente con sus facturas ======");
			System.out.println(client);

			// buscamos al mismo cliente con las facturas guardadas
			Optional<Client> optionalClientDb = clientRepository.findOne(1L);

			optionalClientDb.ifPresentOrElse(clientDb -> {
				// buscamos una factura en la base de datos
				Optional<Invoice> optionalInvoice =  invoiceRepository.findById(1L);

				optionalInvoice.ifPresent(invoice -> {
					// para esto creamos metodo hashCode e equals, porque si no elimina por instacnia y no por objeto
					clientDb.removeInvoice(invoice);
					invoice.setClient(null);

					// guardamos e implimimos cliente con las facturas eliminadas
					clientRepository.save(clientDb);
					
					System.out.println("===== Cliente con una factura eliminada ======");
					System.out.println(clientDb);
				});

			}, () -> System.out.println("El cliente no esta en la base de datos!"));
		}, () -> System.out.println("El cliente no esta en la base de datos!"));

	}

	public void oneToManyBidirectionalFindById(){
		// buscamos un cliente en la base de datos
		Optional<Client> optionalClient = clientRepository.findOne(2L);

		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Factura de oficina", 2500L);
			Invoice invoice2 = new Invoice("Factura de deportes", 500L);
			Invoice invoice3 = new Invoice("Factura de la casa", 470L);

			client.addInvoice(invoice1)
				  .addInvoice(invoice2)
				  .addInvoice(invoice3);

			clientRepository.save(client);

			System.out.println("====== Buscamos un cliente en la db y le asignamos dos facturas");
			System.out.println(client);
		}, () -> System.out.println("El cliente no se encuentra en la db!!!"));

	}

	public void oneToManyBidirectional(){
		// creamos cliente nuevo
		Client client = new Client("Juan", "Elias");

		// creamos dos facturas 
		Invoice invoice1 = new Invoice("Factura de oficina", 2500L);
		Invoice invoice2 = new Invoice("Factura de deportes", 500L);

		// agregamos las facturas al cliente
		client.addInvoice(invoice1)
			  .addInvoice(invoice2);
		
		// guradamos cliente en la db 
		clientRepository.save(client);

		System.out.println("====== Creamos cliente y le asignamos facturas ======");
		System.out.println(client);

	}

	@Transactional
	public void removeAddressFindById(){

		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresentOrElse(client -> {
			client.setAddresses(Arrays.asList(
				new Address("Viamonte", 444),
				new Address("Savedra", 890)
			));
			// Address address1 = new Address("Mitre", 274);
			// Address address2 = new Address("Lima", 564);
			// client.setAddresses(Arrays.asList(address1, address2));
 
			clientRepository.save(client);

			System.out.println("====== Buscamos un cliente por el ID y le asignamos direcciones ======");
			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findById(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(1);
				clientRepository.save(c);
				System.out.println("====== Si el cliente está presente eliminamos una direccion ======");
				System.out.println(c);
			});
			

		}, () -> System.out.println("El cliente no esta en la base de datos!!!"));
	}

	@Transactional
	public void removeAddress(){
		Client client = new Client("Exequiel", "Carrizo");
		
		Address address1 = new Address("Mitre", 209);
		Address address2 = new Address("La Valle", 2211);
		
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);
		System.out.println("====== Cliente nuevo, con una lista de direcciones ======");
		System.out.println(client);

		Optional<Client> currentClient = clientRepository.findById(3L);

		currentClient.ifPresentOrElse(c -> { 
			c.getAddresses().remove(address1);

			clientRepository.save(c);
			System.out.println("====== Creamos un cliente, lo buscamos y si existe eliminamos su direccion ======");
			System.out.println(c);
		}, () -> System.out.println("El cliente no existe en la base de datos!!"));
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
