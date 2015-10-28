package com.modulos.libreria.listadodetallelibreria.xml;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by h on 10/10/15.
 */
public abstract class AbstractXML_SAX<T> {
    public AbstractXML_SAX() {
    }

    protected abstract AbstractManejadorXML createHandler();

    public List<T> leerXML(InputStream is) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();

        AbstractManejadorXML manejador = createHandler();
        reader.setContentHandler(manejador);
        reader.parse(new InputSource(is));

        return manejador.getLstElements();
    }
}
