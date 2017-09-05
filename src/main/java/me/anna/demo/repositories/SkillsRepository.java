package me.anna.demo.repositories;


import me.anna.demo.models.Skills;
import org.springframework.data.repository.CrudRepository;

public interface SkillsRepository extends CrudRepository<Skills,Long> {

    Iterable<Skills> findAllByPersonOrderById(Long id);
//    Iterable<Skills> findAllByPersonIdOrderBySkillId(Long id);
//    findAllByOrderByIdAsc();
}
