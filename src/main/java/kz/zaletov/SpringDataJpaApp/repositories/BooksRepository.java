package kz.zaletov.SpringDataJpaApp.repositories;

import kz.zaletov.SpringDataJpaApp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    public List<Book> findByNameContaining(String containingStr);

}
