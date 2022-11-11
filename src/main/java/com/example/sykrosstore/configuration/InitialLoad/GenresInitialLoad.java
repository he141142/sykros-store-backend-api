package com.example.sykrosstore.configuration.InitialLoad;

import com.example.sykrosstore.entities.Genres;
import com.example.sykrosstore.helper.xmlUtils.XMLHelperFactory;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@Component
public class GenresInitialLoad extends InitialLoad {
    List<Genres> genresList;
    XMLHelperFactory xmlHelperFactory;
    public GenresInitialLoad(String __loc,String __fName){
        super(__loc,__fName);
        this.xmlHelperFactory = new XMLHelperFactory(this.getAbsolutePath());
    }

    public GenresInitialLoad loadGenres() throws XMLStreamException, IOException {
        this.genresList = this.xmlHelperFactory.getGenres();
        return this;
    }
    public List<Genres> getGenres(){
        return this.genresList;
    }

    public List<Genres> getGenresFromXML() throws XMLStreamException, IOException {
        return this.xmlHelperFactory.getGenres();
    }

}
