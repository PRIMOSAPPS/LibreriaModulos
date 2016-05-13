package com.modulos.libreria.utilidadeslibreria.util;

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
public class UtilPropiedades {
	private final static String TAG = "UtilPropiedades";
	/**
	 * Nombre del fichero de propiedades.
	 */
	private final static String NOMBRE_FICHERO_CONEXION = "propiedades_aplicacion.properties";
	/**
	 * Nombre de la apicacion para almacenamiento externo
	 */
	public static final String PROP_NOMBRE_APLICACION = "NombreAplicacion";

	private static UtilPropiedades instancia = null;
	
	/**
	 * Las propiedades leidas del fichero.
	 */
	private Properties properties;

	private UtilPropiedades() {
	}
	
	public static UtilPropiedades getInstance() {
		if(instancia == null) {
			instancia = new UtilPropiedades();
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
			is = assetManager.open(NOMBRE_FICHERO_CONEXION);
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
