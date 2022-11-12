package com.example.sykrosstore.helper.xmlUtils;

import com.example.sykrosstore.entities.Genres;
import com.example.sykrosstore.entities.Subgenres;

import javax.xml.stream.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLHelperFactory {
    XMLInputFactory xmlInputFactory;
    String absolutePath;

    public XMLHelperFactory(String absolutePath) {
        this.xmlInputFactory = XMLInputFactory.newInstance();
        this.absolutePath = absolutePath;
    }

    public List<Genres> getGenres() throws IOException, XMLStreamException {
        XMLStreamReader reader = xmlInputFactory
                .createXMLStreamReader(Files
                        .newInputStream(Paths
                                .get(this.absolutePath)));
        List<Genres> genresList = new ArrayList<>();
        Genres genres = null;
        String tagContent = "";
        boolean SubGenresLevel = false;
        List<Subgenres> subgenres = null;
        Subgenres subGenresEntity = null;
        while (reader.hasNext()) {
            int event = reader.next();


            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "SubGenresList":
                            SubGenresLevel = true;
                            subgenres = new ArrayList<>();
                            break;
                        case "Genres":
                            SubGenresLevel = false;
                            genres = new Genres();
                            break;
                        case "SubGenres":
                            subGenresEntity = new Subgenres();
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent += reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "name":
                            if (SubGenresLevel) {
                                assert subGenresEntity != null;
                                subGenresEntity.setName(tagContent);
                            } else {
                                assert genres != null;
                                genres.setName(tagContent);
                            }
                            break;

                        case "description":
                            if (SubGenresLevel) {
                                assert subGenresEntity != null;
                                subGenresEntity.setDescription(tagContent);
                            }
                            break;

                        case "SubGenres":
                            assert subgenres != null;
                            subgenres.add(subGenresEntity);
                            break;

                        case "SubGenresList":
                            assert genres != null;
                            assert subgenres != null;
                            genres.setSubGenresList(subgenres);
                            break;
                        case "Genres":
                            genresList.add(genres);
                    }
                    tagContent = "";
                    break;
            }
        }
        return genresList;
    }

}
