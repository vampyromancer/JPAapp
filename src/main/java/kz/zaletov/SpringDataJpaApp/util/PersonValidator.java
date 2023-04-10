package kz.zaletov.SpringDataJpaApp.util;

import kz.zaletov.SpringDataJpaApp.DAO.PersonDAO;
import kz.zaletov.SpringDataJpaApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDao;
    @Autowired
    public PersonValidator(PersonDAO personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}
