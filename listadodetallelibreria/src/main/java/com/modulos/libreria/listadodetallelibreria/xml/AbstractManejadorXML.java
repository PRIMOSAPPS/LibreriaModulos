package com.modulos.libreria.listadodetallelibreria.xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsea un XML para convertir su contenido en una lista de sitios.
 * 
 * @author h
 *
 */
public class AbstractManejadorXML<T> extends DefaultHandler {
	protected StringBuilder cadena;
	protected List<T> lstObjetos = null;
	protected T objeto;
	
	@Override
	public void startDocument() throws SAXException {
		cadena = new StringBuilder();
		lstObjetos = new ArrayList<T>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		cadena.append(ch, start, length);
	}

	public List<T> getLstElements() {
		return lstObjetos;
	}


}
