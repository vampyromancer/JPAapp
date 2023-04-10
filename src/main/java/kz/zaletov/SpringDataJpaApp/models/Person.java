package kz.zaletov.SpringDataJpaApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name shouldn not be empty")
    @Size(min=2, max=100, message = "Name should be between 2 and 30 chars")
    @Column(name = "name")
    private String name;
    @Min(value = 1900, message = "Should be real year 0")
    @Max(value = 2020, message = "Should be less than 2020")
    @Column(name = "year")
    private int year;
    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;
    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

}
