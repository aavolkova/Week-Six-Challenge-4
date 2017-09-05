package me.anna.demo.repositories;


import me.anna.demo.models.Education;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepository extends CrudRepository<Education, Long> {
//    List<Education> findAll();
}
