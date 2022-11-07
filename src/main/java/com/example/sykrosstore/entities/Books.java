package com.example.sykrosstore.entities;

import com.example.sykrosstore.constants.EntityValidators.BookValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Books extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotEmpty(message = BookValidator.TITLE_MANDATORY)
  @NotNull(message = BookValidator.TITLE_MANDATORY)
  @JsonProperty("title")
  String title;

  @NotEmpty(message = BookValidator.EDITION_MANDATORY)
  @NotNull(message = BookValidator.EDITION_MANDATORY)
  @JsonProperty("edition")
  String edition;

  @NotNull(message = BookValidator.PUBLICATION_DATE_MANDATORY)
  @JsonFormat(pattern="yyyy-MM-dd")
  Date publicationDate;

  @NotNull Integer availableQuantity = 0;

  @JsonProperty("page")
  @NotNull Integer page;

  @NotNull(message = BookValidator.DESCRIPTION_MANDATORY)
  @NotEmpty(message = BookValidator.DESCRIPTION_MANDATORY)
  @JsonProperty("description")
  String description;

  @JsonProperty("price")
  @NotNull(message = BookValidator.PRICE_MANDATORY)
  Double price;

  @Column(name = "sale_prices")
  Double salePrice;

  Integer pageNumber;

  String Dimensions;

  String language;

  String type;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  Authors author;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id")
  Publisher publisher;


  @OneToMany(mappedBy = "book")
  List<BookImages> bookImages = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  Set<Role> roles = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "books_tags",
      joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  Set<Tags> tags = new HashSet<>();

}
