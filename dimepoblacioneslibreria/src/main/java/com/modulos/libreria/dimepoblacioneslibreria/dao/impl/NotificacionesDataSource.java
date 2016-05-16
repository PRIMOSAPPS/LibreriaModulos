package com.modulos.libreria.dimepoblacioneslibreria.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.modulos.libreria.dimepoblacioneslibreria.dao.NotificacionesSQLite;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controla el acceso a la tabla de las categorias
 * @author h
 *
 */
public class NotificacionesDataSource extends AbstractDataSource {
	private static String[] allColumns = { NotificacionesSQLite.COLUMNA_ID,
			NotificacionesSQLite.COLUMNA_ID_CATEGORIA,
			NotificacionesSQLite.COLUMNA_TITULO,
			NotificacionesSQLite.COLUMNA_TEXTO,
			NotificacionesSQLite.COLUMNA_FECHA_INICIO_VALIDEZ,
			NotificacionesSQLite.COLUMNA_FECHA_FIN_VALIDEZ,
			NotificacionesSQLite.COLUMNA_ULTIMA_ACTUALIZACION
	};

	public NotificacionesDataSource(Context context) {
		super(context);
	}

	@Override
	protected SQLiteOpenHelper crearDbHelper(Context context) {
		return new NotificacionesSQLite(context);
	}

	public long insertar(NotificacionDTO categoria) {
		ContentValues valores = eventoToObject(categoria);
		return database.insert(NotificacionesSQLite.TABLE_NAME, null, valores);
	}
	
	/**
	 * Devuelve una categoria por su identificador
	 * @param id
	 * @return
	 */
	public NotificacionDTO getById(long id) {
		NotificacionDTO resul = null;
		String where = NotificacionesSQLite.COLUMNA_ID + " = " + id;
		Cursor cursor = database.query(NotificacionesSQLite.TABLE_NAME,
				allColumns, where, null, null, null, null);
		cursor.moveToFirst();
	    if(!cursor.isAfterLast()) {
			resul = cursorToObject(cursor);
		}
		cursor.close();
		
		return resul;
	}

	/**
	 * Devuelve las notificaciones que pertenecen a una categoria
	 * @param idCategoria
	 * @return
	 */
	public List<NotificacionDTO> getByCategoria(long idCategoria) {
		List<NotificacionDTO> resul = new ArrayList<>();
		String where = NotificacionesSQLite.COLUMNA_ID_CATEGORIA + " = '" + idCategoria + "'";
		Cursor cursor = database.query(NotificacionesSQLite.TABLE_NAME,
				allColumns, where, null, null, null, NotificacionesSQLite.COLUMNA_FECHA_INICIO_VALIDEZ + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			NotificacionDTO categoria = cursorToObject(cursor);

			resul.add(categoria);
			cursor.moveToNext();
		}
		cursor.close();
		
		return resul;
	}

	/**
	 * Devuelve todas las categorias existentes en la base de datos.
	 * @return
	 */
	public List<NotificacionDTO> getAll() {
		List<NotificacionDTO> resul = new ArrayList<>();

		Cursor cursor = database.query(NotificacionesSQLite.TABLE_NAME,
				allColumns, null, null, null, null, NotificacionesSQLite.COLUMNA_FECHA_INICIO_VALIDEZ + " DESC");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			NotificacionDTO categoria = cursorToObject(cursor);

			resul.add(categoria);
			cursor.moveToNext();
		}
		cursor.close();
		return resul;
	}
	
	/**
	 * Actualiza la categoria recibida en la base de datos.
	 * @param categoria
	 * @return
	 */
	public long actualizar(NotificacionDTO categoria) {
		ContentValues valores = eventoToObject(categoria);
		return database.update(NotificacionesSQLite.TABLE_NAME, valores,
				NotificacionesSQLite.COLUMNA_ID + "=" + categoria.getId(), null);
	}

	/**
	 * Borra la categoria recibida de la base de datos.
	 * @param categoria
	 */
	public void delete(NotificacionDTO categoria) {
		long id = categoria.getId();
		database.delete(NotificacionesSQLite.TABLE_NAME, NotificacionesSQLite.COLUMNA_ID
				+ " = " + id, null);
	}

	/**
	 * Elimina las notificaciones cuya fecha de fin de validez es anterior a la actual
	 * @return
	 */
	public void eliminarPasadas() {
		long fechaHoy = new Date().getTime();
		String whereClause = NotificacionesSQLite.COLUMNA_FECHA_FIN_VALIDEZ + " < ?";
		String[] whereArgs = {Long.toString(fechaHoy)};
		database.delete(NotificacionesSQLite.TABLE_NAME, whereClause, whereArgs);
	}

	/**
	 * Convierte un cursor recibido de una consulta a la base de datos en un objeto NotificacionDTO.
	 * @param cursor
	 * @return
	 */
	private NotificacionDTO cursorToObject(Cursor cursor) {
		NotificacionDTO resul = new NotificacionDTO();
		resul.setId(cursor.getLong(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_ID)));
		resul.setIdCategoria(cursor.getLong(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_ID_CATEGORIA)));
		resul.setTitulo(cursor.getString(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_TITULO)));
		resul.setTexto(cursor.getString(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_TEXTO)));
		long fechaInicioValidez = cursor.getLong(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_FECHA_FIN_VALIDEZ));
		resul.setFechaInicioValidez(new Date(fechaInicioValidez));
		long fechaFinValidez = cursor.getLong(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_FECHA_FIN_VALIDEZ));
		resul.setFechaFinValidez(new Date(fechaFinValidez));
		long ultimaActualizacion = cursor.getLong(cursor.getColumnIndex(NotificacionesSQLite.COLUMNA_ULTIMA_ACTUALIZACION));
		resul.setUltimaActualizacion(new Date(ultimaActualizacion));

		return resul;
	}

	/**
	 * Convierte un objeto categoria en un objeto ContentValues que podra ser usado para insercion/actualizacion
	 * de la base de datos.
	 * @param categoria
	 * @return
	 */
	private ContentValues eventoToObject(NotificacionDTO categoria) {
		ContentValues valores = new ContentValues();
		valores.put(NotificacionesSQLite.COLUMNA_ID, categoria.getId());
		valores.put(NotificacionesSQLite.COLUMNA_ID_CATEGORIA, categoria.getIdCategoria());
		valores.put(NotificacionesSQLite.COLUMNA_TITULO,
				categoria.getTitulo());
		valores.put(NotificacionesSQLite.COLUMNA_TEXTO,
				categoria.getTexto());
		valores.put(NotificacionesSQLite.COLUMNA_FECHA_INICIO_VALIDEZ,
				categoria.getFechaInicioValidez().getTime());
		valores.put(NotificacionesSQLite.COLUMNA_FECHA_FIN_VALIDEZ, categoria
				.getFechaFinValidez().getTime());
		valores.put(NotificacionesSQLite.COLUMNA_ULTIMA_ACTUALIZACION, categoria
				.getUltimaActualizacion().getTime());
		return valores;
	}

	/**
	 * Devuelve la fecha de la ultima actualizacion de la tabla
	 * @return
	 */
	public long getUltimaActualizacion() {
		String sql = "SELECT MAX(" + NotificacionesSQLite.COLUMNA_ULTIMA_ACTUALIZACION + ") FROM " +
				NotificacionesSQLite.TABLE_NAME;
		String[] bindVars = {};
		Cursor cursor = database.rawQuery(sql, bindVars);

		long ultimaActualizacion = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()) {
			ultimaActualizacion = cursor.getLong(0);
		}
		cursor.close();
		return ultimaActualizacion;
	}
}