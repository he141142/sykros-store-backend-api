package com.example.sykrosstore.internal.controller.dto.book;

import com.example.sykrosstore.entities.Authors;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Publisher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class UpdateBook {
    @JsonProperty("edition")
    String edition = null;

    @JsonProperty("title")
    String title = null;

    @JsonProperty("description")
    String description = null;

    @JsonProperty("price")
    Double price = null;

    @JsonProperty("sale_price")
    Double salePrice = null;

    Integer pageNumber = null;

    @JsonProperty("language")
    String language = null;

    @JsonProperty("type")
    String type = null;

    @JsonProperty("author")
    @Valid()
    Authors author = null;

    @JsonProperty("publisher")
    Publisher publisher = null;


    public Books bindBook(Books books){
        String edition = this.edition;
        String title = this.title;
        String description = this.description;
        Double price = this.price;
        Authors author = this.author;
        Publisher publisher = this.publisher;
        String language = this.language;
        Double salePrice = this.salePrice;
        if (this.getPrice()!=null)books.setPrice(price);
        if (title!=null) books.setTitle(title);
        if (author!=null) books.setAuthor(author);
        if (language!=null) books.setLanguage(language);
        if (publisher!=null) books.setPublisher(publisher);
        if (description!=null) books.setDescription(description);
        if (edition!=null) books.setEdition(edition);
        if (salePrice!=null) books.setSalePrice(salePrice);
        if (this.type != null) books.setType(this.type);
        return books;
    }

}
