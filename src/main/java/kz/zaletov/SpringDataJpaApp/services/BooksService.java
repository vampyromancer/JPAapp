package kz.zaletov.SpringDataJpaApp.services;

import kz.zaletov.SpringDataJpaApp.models.Book;
import kz.zaletov.SpringDataJpaApp.models.Person;
import kz.zaletov.SpringDataJpaApp.repositories.BooksRepository;
import kz.zaletov.SpringDataJpaApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }
    public List<Book> findAll(String str){
        return booksRepository.findAll(Sort.by(str));
    }
    public List <Book> findAll(int page, int itemsPerPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }
    public List <Book> findAll(int page, int itemsPerPage, String str){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(str))).getContent();
    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id); //либо можно сделать скрытой поле в форме edit, тогда не нужно бы было его назначать
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public void setOwner(int id, Person person){
        booksRepository.findById(id).ifPresent(book-> {
            book.setOwner(person);
            book.setCreatedAt(new Date());
        });
    }
    @Transactional
    public void removeOwner(int id){
        booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }
    @Transactional
    public void checkTime(Book book) {
        if (book.getCreatedAt() != null) {
            int dif = (int) ((new Date().getTime() - book.getCreatedAt().getTime()) / 86400000);
            if (dif > 10)
                book.setItLate(true);
        }
    }

    public void test(){
        System.out.println("Testing here with debug. Inside Hibernate Transaction");
    }

    public List<Book> search(String str) {
        return booksRepository.findByNameContaining(str);
    }
}
