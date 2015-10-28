package com.modulos.libreria.dimepoblacioneslibreria.xml;

import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;

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
 * Lee los datos del XML recibido como un InputStream y devuelve una lista de los objetos resultantes.
 * Para ello se sirve de las clases ManejadorCategoriasXML, ManejadorSitiosXML o ManejadorEventosXML
 * segun corresponda
 * @author h
 *
 */
public class EventosXML_SAX {
	public EventosXML_SAX() {
	}
	
	public List<CategoriaDTO> leerCategoriasXML(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		
		ManejadorCategoriasXML manejador = new ManejadorCategoriasXML();
		reader.setContentHandler(manejador);
		reader.parse(new InputSource(is));
		
		return manejador.getLstElements();
	}

	public List<SitioDTO> leerSitiosXML(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		
		ManejadorSitiosXML manejador = new ManejadorSitiosXML();
		reader.setContentHandler(manejador);
		reader.parse(new InputSource(is));
		
		return manejador.getLstElements();
	}

}
