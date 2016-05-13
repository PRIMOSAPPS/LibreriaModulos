package com.modulos.libreria.utilidadeslibreria.almacenamiento;

import android.content.Context;
import android.os.Environment;

import com.modulos.libreria.utilidadeslibreria.util.UtilPropiedades;

import java.io.File;

/**
 * Clase que se encarga de guardar y recuperar las imagenes en almacenamiento externo.
 * Crea una estructura de directorios que empieza con un directorio cuyo nombre se corresponde con
 * el valor de la constante NOMBRE_APP. Dentro de este directorio se crean 3 subdirectorios (categorias,
 * eventos y sitios) y dentro de estos subdirectorios se crea uno por cada identificador de un contenido.
 * 
 * Por ejemplo, las imagenes de un sitio con id = 1 estarian en la ruta <b>NOMBRE_APP/sitios/1/</b> asi
 * se mantienen separadas las imagenes de los distintos contenidos.
 * Lo he hecho asi por que creo que sera mas dificil tener problemas con los nombres y tambien esta mas organizado.
 * Una vez hecha la clase solo es utilziarla para conseguir las imagenes.
 * 
 * @author h
 *
 */
public class AlmacenamientoUtilExterno implements ItfAlmacenamiento {
	private final static String TAG = "[AlmacenamientoUtilExterno]";
	
	/**
	 * Directorio de almacenamiento externo por defecto para la aplicacion. 
	 */
	private static String dirExterno;

	public AlmacenamientoUtilExterno() {
		File fileDirExterno = Environment.getExternalStorageDirectory();
		dirExterno = fileDirExterno.getAbsolutePath();
	}

	/**
	 * Devuelve la ruta de almacenamiento externo para la aplicacion concatenado con el nombre de la constante NOMBRE_APP
	 * @return
	 */
	private String getDirApp() {
		String nombreAplicacion = UtilPropiedades.getInstance().getProperty(UtilPropiedades.PROP_NOMBRE_APLICACION);
		return dirExterno + File.separator + nombreAplicacion;
	}

	@Override
	public File crearFicheroTemporal(String part, String ext) throws Exception {
		File tempDir=new File(getDirApp()+"/.temp/");
		if(!tempDir.exists()) {
			tempDir.mkdirs();
		}
		return File.createTempFile(part, ext, tempDir);
	}

}
