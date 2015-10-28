package com.modulos.libreria.dimepoblacioneslibreria.xml;

import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.utilidadeslibreria.util.ConversionesUtil;
import com.modulos.libreria.utilidadeslibreria.util.UtilFechas;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Parsea un XML para convertir su contenido en una lista de categorias.
 * @author h
 *
 */
public class ManejadorCategoriasXML extends DefaultHandler {
	private final static String TAG = "ManejadorCategoriasXML";
	private StringBuilder cadena;
	private List<CategoriaDTO> lstCategorias = null;
	private CategoriaDTO categoria;
	
	@Override
	public void startDocument() throws SAXException {
		cadena = new StringBuilder();
		lstCategorias = new ArrayList<CategoriaDTO>();
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
			if(localName.equals("idCategoria")) {
				categoria.setId(Long.parseLong(cadena.toString()));
			} else if(localName.equals("nombre")) {
				categoria.setNombre(cadena.toString());
			} else if(localName.equals("descripcion")) {
				categoria.setDescripcion(cadena.toString());
			} else if(localName.equals("nombreIcono")) {
				categoria.setNombreIcono(cadena.toString());
			} else if(localName.equals("numeroSitios")) {
				categoria.setNumeroSitios(Integer.parseInt(cadena.toString()));
			} else if(localName.equals("icono")) {
//				byte[] strImagenBase64 = cadena.toString().trim().getBytes("UTF-16"); ;
//				byte[] base64decoded = Base64.decode(strImagenBase64, Base64.DEFAULT);
//				Bitmap icono = BitmapFactory.decodeByteArray(base64decoded, 0, base64decoded.length);
				categoria.setIcono(ConversionesUtil.getBitmap(cadena));
			} else if(localName.equals("ultimaActualizacion")) {
				Date ultimaActualizacionDefault = UtilFechas.fechaFromUTC(cadena.toString().trim());
				categoria.setUltimaActualizacion(ultimaActualizacionDefault);
			} else if(localName.equals("categoria")) {
				lstCategorias.add(categoria);
			}
		} catch(Exception e) {
			throw new SAXException("Error al leer una fecha #" + cadena.toString().trim() + "#", e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		cadena.setLength(0);
		if(localName.equals("categoria")) {
			Log.w("CATEGORIAS: ", "Una categoria nueva");
			categoria = new CategoriaDTO();
		}
	}

	public List<CategoriaDTO> getLstElements() {
		return lstCategorias;
	}


}
