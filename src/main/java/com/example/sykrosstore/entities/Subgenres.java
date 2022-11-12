package com.example.sykrosstore.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
public class Subgenres  extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull @NotEmpty String name;

  @NotNull
  @NotEmpty
  @Column(length = 2555,name = "description")
  String description;

  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @JsonFormat(pattern="yyyy/MM/dd")
  @Column(name = "created_at")
  @NotNull
  private Date createdAt = new Date();

  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @JsonFormat(pattern="yyyy/MM/dd")
  @Column(name = "updated_at")
  @NotNull
  private Date updatedAt = new Date();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genres_id")
  @JsonIgnore
  Genres genres;

  @JsonIgnore
  @OneToMany(mappedBy = "subgenres")
  List<BookGenres> bookGenres = new ArrayList<>();

  @ManyToMany
  @JoinTable(
          name = "book_genres",
          joinColumns = @JoinColumn(name = "subgenres_id"),
          inverseJoinColumns = @JoinColumn(name = "book_id"))
  List<Books> booksList = new ArrayList<>();
}
