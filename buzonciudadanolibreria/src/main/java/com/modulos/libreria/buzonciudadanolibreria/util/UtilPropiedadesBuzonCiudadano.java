package com.modulos.libreria.buzonciudadanolibreria.util;

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
public class UtilPropiedadesBuzonCiudadano {
	private final static String TAG = "UtilPropiedadesBuzonCiu";
	/**
	 * Nombre del fichero de propiedades.
	 */
	private final static String NOMBRE_FICHERO_PROPIEDADES = "propiedades_buzon_ciudano.properties";
	/**
	 * Propiedad del fichero de propiedades que indica el identificador de poblacion para realizar las peticiones de actualizacion de datos.
	 */
	public final static String PROP_URL_ENVIO_CORREO = "urlEnvioCorreo";

	private static UtilPropiedadesBuzonCiudadano instancia = null;
	
	/**
	 * Las propiedades leidas del fichero.
	 */
	private Properties properties;

	private UtilPropiedadesBuzonCiudadano() {
	}
	
	public static UtilPropiedadesBuzonCiudadano getInstance() {
		if(instancia == null) {
			instancia = new UtilPropiedadesBuzonCiudadano();
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
			is = assetManager.open(NOMBRE_FICHERO_PROPIEDADES);
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
