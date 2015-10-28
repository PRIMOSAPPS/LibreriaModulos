package com.modulos.libreria.listadodetallelibreria.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase padre del resto de clases XXXDataSource existentes.
 * <b>Creo que se pueden mover mas metodos a esta clase, pero todavia no los he movido</b>
 * 
 * Estas clases se usaran acceder/actualizar a los contenidos de la base de datos
 * <b>No se accede directamente a traves de las clases XXXSQLite</b>
 * @author h
 *
 */
public abstract class AbstractDataSource<T> {
	protected SQLiteDatabase database;
	protected SQLiteOpenHelper dbHelper;
	protected Context context;

	public AbstractDataSource(Context context) {
		this.context = context;
		dbHelper = crearDbHelper(context);
	}
	
	protected abstract SQLiteOpenHelper crearDbHelper(Context context);

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long insertar(T categoria) {
		ContentValues valores = eventoToObject(categoria);
		return database.insert(getTableName(), null, valores);
	}

	/**
	 * Devuelve la fecha de la ultima actualizacion de la tabla
	 * @return
	 */
	public long getUltimaActualizacion() {
		String sql = "SELECT MAX(" + getColumnaUltimaActualizacion() + ") FROM " +
				getTableName();
		Cursor cursor = database.rawQuery(sql, null);

		long ultimaActualizacion = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()) {
			ultimaActualizacion = cursor.getLong(0);
		}
		cursor.close();
		return ultimaActualizacion;
	}

	/**
	 * Devuelve una categoria por su identificador
	 * @param id
	 * @return
	 */
	public T getById(long id) {
		String where = getColumnaId() + " = " + id;
		Cursor cursor = database.query(getTableName(),
				getAllColumns(), where, null, null, null, null);
		return getFirstByCursor(cursor);
	}

	protected T getFirstByCursor(Cursor cursor) {
		T resul = null;

		cursor.moveToFirst();
		if(!cursor.isAfterLast()) {
			resul = cursorToObject(cursor);
		}
		cursor.close();

		return resul;
	}

	protected List<T> getByCursor(Cursor cursor) {
		List<T> resul = new ArrayList<T>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T objeto = cursorToObject(cursor);

			resul.add(objeto);
			cursor.moveToNext();
		}
		cursor.close();
		return resul;
	}

	/**
	 * Devuelve todas los objetos existentes en la tabla de la base de datos.
	 * @return
	 */
	public List<T> getAll() {
		Cursor cursor = database.query(getTableName(),
				getAllColumns(), null, null, null, null, null);
		return getByCursor(cursor);
	}

	protected abstract String[] getAllColumns();

	protected abstract long getId(T objeto);

	protected abstract String getTableName();

	protected abstract String getColumnaId();

	protected abstract String getColumnaUltimaActualizacion();

	/**
	 * Actualiza la objeto recibida en la base de datos.
	 * @param objeto
	 * @return
	 */
	public long actualizar(T objeto) {
		ContentValues valores = eventoToObject(objeto);
		return database.update(getTableName(), valores,
				getColumnaId() + "=" + getId(objeto), null);
	}

	/**
	 * Borra la objeto recibido de la base de datos.
	 * @param objeto
	 */
	public void delete(T objeto) {
		database.delete(getTableName(), getColumnaId()
				+ " = " + getId(objeto), null);
	}

	/**
	 * Convierte un cursor recibido de una consulta a la base de datos en un objeto.
	 * @param cursor
	 * @return
	 */
	protected abstract T cursorToObject(Cursor cursor);

	/**
	 * Convierte un objeto recibido en un objeto ContentValues que podra ser usado para insercion/actualizacion
	 * de la base de datos.
	 * @param objeto
	 * @return
	 */
	protected abstract ContentValues eventoToObject(T objeto);

}
