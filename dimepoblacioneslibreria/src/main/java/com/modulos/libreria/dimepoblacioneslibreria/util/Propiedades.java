package com.modulos.libreria.dimepoblacioneslibreria.util;

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
public class Propiedades {
	private final static String TAG = "UtilPropiedadesDime";
	/**
	 * Nombre del fichero de propiedades.
	 */
	private final static String NOMBRE_FICHERO_CONEXION = "propiedades_aplicacion.properties";
	/**
	 * Propiedad del fichero de propiedades que indica el identificador de poblacion para realizar las peticiones de actualizacion de datos.
	 */
	public final static String PROP_ID_POBLACION = "idPoblacion";
	/**
	 * Propiedad del fichero de propiedades que indica el servidor al que se conecta para realziar las peticiones de datos
	 */
	public final static String PROP_SERVIDOR = "Servidor";
	/**
	 * Ruta en el servidor para pedir los datos de actualizacion de las categorias.
	 */
	public final static String PROP_RUTA_CATEGORIAS_XML = "RutaCategoriasXML";
	/**
	 * Ruta en el servidor para pedir los datos de actualizacion de los sitios.
	 */
	public final static String PROP_RUTA_SITIOS_XML = "RutaSitiosXML";
	/**
	 * Ruta en el servidor para pedir los datos de actualizacion de los sitios.
	 */
	public final static String PROP_RUTA_NOTIFICACIONES_XML = "RutaNotificacionesXML";
	/**
	 * Ruta en el servidor para pedir los datos de actualizacion de los sitios.
	 */
	public final static String PROP_RUTA_NOTIFICACIONES_ACTUALIZABLES_XML = "RutaNotificacionesActualizablesXML";
	/**
	 * ApplicationId para registrarse en Parse y recibir las notificaciones push
	 */
	public final static String PROP_PARSE_APPLICATION_ID = "parseApplicationId";
	/**
	 * ClientKey para registrarse en Parse y recibir las notificaciones push
	 */
	public final static String PROP_PARSE_CLIENT_KEY = "parseClientKey";
	/**
	 * Url de registro de la aplicación
	 */
	public static final String PROP_URL_REGISTRO = "UrlRegistro";
	/**
	 * Direccion de correo para la aplicacion.
	 */
	public static final String PROP_CORREO_APLICACION = "CorreoAplicacion";
	/**
	 * Direccion para escuchar la radio en la aplicacion.
	 */
	public static final String PROP_URL_RADIO = "urlRadio";
	/**
	 * Direccion para escuchar la radio en la aplicacion.
	 */
	public static final String PROP_URL_VIDEO_PROMOCION = "urlVideoPromocion";
	/**
	 * Latitud en la que se abre el mapa.
	 */
	public static final String PROP_LATITUD_PUEBLO = "latitudPueblo";
	/**
	 * Longitud en la que se abre el mapa
	 */
	public static final String PROP_LONGITUD_PUEBLO = "longitudPueblo";

}
