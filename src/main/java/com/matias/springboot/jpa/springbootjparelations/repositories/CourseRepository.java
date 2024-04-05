package com.matias.springboot.jpa.springbootjparelations.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.springboot.jpa.springbootjparelations.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{

}
