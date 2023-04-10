package kz.zaletov.SpringDataJpaApp.repositories;

import kz.zaletov.SpringDataJpaApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
