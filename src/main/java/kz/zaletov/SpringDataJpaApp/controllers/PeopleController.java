package kz.zaletov.SpringDataJpaApp.controllers;

import jakarta.validation.Valid;
import kz.zaletov.SpringDataJpaApp.DAO.BooksDAO;
import kz.zaletov.SpringDataJpaApp.DAO.PersonDAO;
import kz.zaletov.SpringDataJpaApp.models.Person;
import kz.zaletov.SpringDataJpaApp.services.BooksService;
import kz.zaletov.SpringDataJpaApp.services.PeopleService;
import kz.zaletov.SpringDataJpaApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PeopleService peopleService;
    private final BooksService booksService;
    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService, BooksService booksService){
        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("people",peopleService.findAll());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findOne(id).getBookList());
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
//        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
//        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
