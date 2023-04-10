package kz.zaletov.SpringDataJpaApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Book name shouldn't be empty")
    @Size(min=1, max=100, message = "Book name should be between 1 and 100 chars")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Author name shouldn't be empty")
    @Size(min=2, max=100, message = "Author name should be between 2 and 100 chars")
    @Column(name = "author")
    private String author;
    @Min(value = 1000, message = "More than 1000")
    @Max(value = 2022, message = "Less than 2022")
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Transient
    private boolean isItLate;

    public Book() {
    }

    public boolean isItLate() {
        return isItLate;
    }

    public void setItLate(boolean itLate) {
        isItLate = itLate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
