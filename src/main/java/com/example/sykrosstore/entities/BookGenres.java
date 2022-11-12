package com.example.sykrosstore.entities;

import javax.persistence.*;

@Entity(name = "book_genres")
public class BookGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    Books books;

    @ManyToOne()
    @JoinColumn(name = "subgenres_id")
    Subgenres subgenres;
}
