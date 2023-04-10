package kz.zaletov.SpringDataJpaApp.controllers;

import jakarta.validation.Valid;
import kz.zaletov.SpringDataJpaApp.models.Book;
import kz.zaletov.SpringDataJpaApp.models.Person;
import kz.zaletov.SpringDataJpaApp.services.BooksService;
import kz.zaletov.SpringDataJpaApp.services.PeopleService;
import kz.zaletov.SpringDataJpaApp.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;
//    private final BookValidator bookValidator;
    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping("")
    public String index(
            @RequestParam(value="sort_by_year", required = false) boolean sortByYear,
            Model model,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="per_page", required = false) Integer perPage){
        if((page!=null)&&(perPage!=null)&&sortByYear){
            model.addAttribute("books", booksService.findAll(page, perPage, "year"));
            return "books/index";
        }
        if(sortByYear){
            model.addAttribute("books", booksService.findAll("year"));
            return "books/index";
        }
        if((page!=null)&&(perPage!=null)){
            model.addAttribute("books", booksService.findAll(page, perPage));
            return "books/index";
        }
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model){
        //проверяем просрочена ли книга, и передаем книгу в модель
        Book book = booksService.findOne(id);
        booksService.checkTime(book);
        model.addAttribute("book", book);
        //отправляем модель со всем списком людей, чтобы можно было назначить владельца
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
//        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
//        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        //в селекте от формы отправляем person, потом назначаем владельца по id
        booksService.setOwner(id, peopleService.findOne(person.getId()));

        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @DeleteMapping("{id}/assign")
    public String returnToLibrary(@PathVariable("id") int id){
        booksService.removeOwner(id);
        return "redirect:/books/{id}";
    }
    @GetMapping("/search")
    public String search(Model model){
        return "books/search";
    }
    @PostMapping("/search")
    public String searchResult(@RequestParam(value = "searchString", required = false) String searchingString, Model model) {
        if (searchingString != null) {
            model.addAttribute("foundBooks", booksService.search(searchingString));
        }
        return "books/search";
    }
}
