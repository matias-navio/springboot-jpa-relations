package com.matias.springboot.jpa.springbootjparelations.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String name;
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // manera de hace una tabla intermedia personalizada, creando la tabla y las columnas
    @JoinTable(
        name = "tbl_clientes_to_direcciones", 
        joinColumns = @JoinColumn(name = "id_cliente"), 
        inverseJoinColumns = @JoinColumn(name = "id_direccion"), 
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direccion"})
    )
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private List<Invoice> invoices;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;

    public Client() {
        this.addresses = new ArrayList<>();
        this.invoices = new ArrayList<>();
    } 

    public Client(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    // seteamos el detail en client y el cliente en el detail
    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this);
    }

    public void removeClientDetails(ClientDetails clientDetails) {
        clientDetails.setClient(null);
        this.clientDetails = null;
    }

    // metodo para agregar facturas
    public Client addInvoice(Invoice invoice){
        // agregamos las facturas que nos pasen por parametro
        invoices.add(invoice);
        // se la asignamos al cliente
        invoice.setClient(this);
        return this;
    }
    // método para eliminar facturas
    public void removeInvoice(Invoice invoice){
        // con el método remove de list eliminamos la factura del parámetro
        invoices.remove(invoice);
        invoice.setClient(null);
    }

    @Override
    public String toString() {
        return "{id=" + id + 
                ", name=" + name + 
                ", latname=" + lastname + 
                ", addresses=" + addresses + 
                ", invoices=" + invoices +
                ", clientDetails=" + clientDetails + 
                "}";
    }

   

}
