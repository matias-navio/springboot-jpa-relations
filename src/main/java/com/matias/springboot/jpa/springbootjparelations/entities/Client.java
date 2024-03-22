package com.matias.springboot.jpa.springbootjparelations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String latname;

    public Client() {
    }

    public Client(String name, String latname) {
        this.name = name;
        this.latname = latname;
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
    public String getLatname() {
        return latname;
    }
    public void setLatname(String latname) {
        this.latname = latname;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", latname=" + latname + "}";
    }

    

}