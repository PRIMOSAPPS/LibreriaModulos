package com.modulos.libreria.parselibreria.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Clase para leer las propiedades de la aplicacion. La he hecho usando el patron singleton
 * para poder unir la lectura de propiedades en una sola clase.
 * @author h
 *
 */
public class ParsePropiedades {
	private final static String TAG = "ParsePropiedades";
	/**
	 * Nombre del fichero de propiedades.
	 */
	private final static String PROPIEDADES_PARSE_PROPERTIES = "propiedades_parse.properties";
//	/**
//	 * Propiedad del fichero de propiedades que indica el identificador de poblacion para realizar las peticiones de actualizacion de datos.
//	 */
//	public final static String PROP_ID_POBLACION = "idPoblacion";
//	/**
//	 * Propiedad del fichero de propiedades que indica el servidor al que se conecta para realziar las peticiones de datos
//	 */
//	public final static String PROP_SERVIDOR = "Servidor";
//	/**
//	 * Ruta en el servidor para pedir los datos de actualizacion de las categorias.
//	 */
//	public final static String PROP_RUTA_CATEGORIAS_XML = "RutaCategoriasXML";
//	/**
//	 * Ruta en el servidor para pedir los datos de actualizacion de los sitios.
//	 */
//	public final static String PROP_RUTA_SITIOS_XML = "RutaSitiosXML";
	/**
	 * ApplicationId para registrarse en Parse y recibir las notificaciones push
	 */
	public final static String PROP_PARSE_APPLICATION_ID = "parseApplicationId";
	/**
	 * ClientKey para registrarse en Parse y recibir las notificaciones push
	 */
	public final static String PROP_PARSE_CLIENT_KEY = "parseClientKey";
//	/**
//	 * Nombre de la apicacion para almacenamiento externo
//	 */
//	public static final String PROP_NOMBRE_APLICACION = "NombreAplicacion";
//	/**
//	 * Url de registro de la aplicación
//	 */
//	public static final String PROP_URL_REGISTRO = "UrlRegistro";
//	/**
//	 * Direccion de correo para la aplicacion.
//	 */
//	public static final String PROP_CORREO_APLICACION = "CorreoAplicacion";

	private static ParsePropiedades instancia = null;

	/**
	 * Las propiedades leidas del fichero.
	 */
	private Properties properties;

	private ParsePropiedades() {
	}
	
	public static ParsePropiedades getInstance() {
		if(instancia == null) {
			instancia = new ParsePropiedades();
		}
		return instancia;
	}
	
	/**
	 * Realiza la lectura del fichero de propiedades;
	 */
	public void inicializar(Context contexto) {
		Log.d(TAG, "Inicializando las propiedades.");
		AssetManager assetManager = contexto.getAssets();
		InputStream is = null;
		properties = new Properties();
		try {
			is = assetManager.open(PROPIEDADES_PARSE_PROPERTIES);
			properties.load(is);
		} catch (IOException e) {
			Log.e(TAG, "Error al leer las propiedades de la aplicacion", e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.e(TAG, "Error al cerrar InputStream del fichero de propiedades", e);
				}
			}
		}
		
	}
	
	public String getProperty(String property) {
		return properties.getProperty(property);
	}

}
