package com.modulos.libreria.listadodetallelibreria.actualizador;

import android.content.Context;
import android.util.Log;

import com.modulos.libreria.listadodetallelibreria.impl.AbstractDataSource;

import java.util.List;

/**
 * Clase que realiza la actualizacion de los datos en la base de datos y almacenamiento externo.
 * Para los nuevos contenidos inserta o actualiza el contenido en la base de datos y para aquellos
 * contenido que tengan imagenes guarda las imagenes en almacenamiento externo.
 * @author h
 *
 */
public abstract class ActualizadorDtos<T> {
	private final static String TAG = "ActualizadorDtos";

	private Context contexto;

	public ActualizadorDtos(Context contexto) {
		this.contexto = contexto;
	}

	protected abstract AbstractDataSource createDataSource(Context contexto);

	protected abstract long getId(T objeto);

	protected abstract void accionesExtra(T objeto);

	/**
	 * Realiza la insercion/actualizacion de los objetos segun la lista de objetos recibidos.
	 * @param lstObjetos
	 */
	public void actualizarObjetos(List<T> lstObjetos)  {
		AbstractDataSource<T> dataSource = createDataSource(contexto);
		try {
			dataSource.open();
			
			Log.d(TAG, lstObjetos.toString());
			for(T objeto : lstObjetos) {
				long id = getId(objeto);
				T existente = dataSource.getById(id);
				if(existente == null) {
					dataSource.insertar(objeto);
				} else {
					dataSource.actualizar(objeto);
				}
				accionesExtra(objeto);
			}
		} finally {
			dataSource.close();
		}
	}

}
