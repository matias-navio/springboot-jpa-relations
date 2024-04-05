package com.matias.springboot.jpa.springbootjparelations.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.springboot.jpa.springbootjparelations.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
