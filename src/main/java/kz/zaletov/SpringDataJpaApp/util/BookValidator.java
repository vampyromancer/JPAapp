package kz.zaletov.SpringDataJpaApp.util;

import kz.zaletov.SpringDataJpaApp.DAO.BooksDAO;
import kz.zaletov.SpringDataJpaApp.models.Book;
import kz.zaletov.SpringDataJpaApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BooksDAO booksDAO;
    @Autowired
    public BookValidator(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}
