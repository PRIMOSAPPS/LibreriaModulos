package com.modulos.libreria.dimepoblacioneslibreria.xml;

import android.util.Base64;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.utilidadeslibreria.util.UtilFechas;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Parsea un XML para convertir su contenido en una lista de sitios.
 * 
 * @author h
 *
 */
public class ManejadorNotificacionesDescargablesXML extends DefaultHandler {
	private StringBuilder cadena;
	private List<NotificacionDTO> lstNotificaciones = null;
	private NotificacionDTO notificacion;
	
	@Override
	public void startDocument() throws SAXException {
		cadena = new StringBuilder();
		lstNotificaciones = new ArrayList<NotificacionDTO>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		cadena.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		try {
			if(localName.equals("idNotificacion")) {
				notificacion.setId(Long.parseLong(cadena.toString()));
			} else if(localName.equals("titulo")) {
				notificacion.setTitulo(stringFromBase64(cadena.toString()));
			} else if(localName.equals("ultimaActualizacion")) {
				Date ultimaActualizacionDefault = UtilFechas.fechaFromUTC(cadena.toString().trim());
				notificacion.setUltimaActualizacion(ultimaActualizacionDefault);
			} else if(localName.equals("notificacion_actualizable")) {
				lstNotificaciones.add(notificacion);
			}
		} catch(Exception e) {
			throw new SAXException("Error al leer una cadena #" + cadena.toString().trim() + "#", e);
		}
	}

	private String stringFromBase64(String txtBase64) {
		String textoBase64 = txtBase64.toString();
		return new String(Base64.decode(textoBase64, Base64.DEFAULT));
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		cadena.setLength(0);
		if(localName.equals("notificacion_actualizable")) {
			Log.w("NOTIFIACIONES: ", "Una notificacion nueva");
			notificacion = new NotificacionDTO();
		}
	}

	public List<NotificacionDTO> getLstElements() {
		return lstNotificaciones;
	}


}
