package com.modulos.libreria.dimepoblacioneslibreria.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.modulos.libreria.dimepoblacioneslibreria.dao.CategoriasSQLite;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controla el acceso a la tabla de las categorias
 * @author h
 *
 */
public class CategoriasDataSource extends AbstractDataSource {
	private static String[] allColumns = { CategoriasSQLite.COLUMNA_ID,
			CategoriasSQLite.COLUMNA_NOMBRE,
			CategoriasSQLite.COLUMNA_DESCRIPCION,
			CategoriasSQLite.COLUMNA_NOMBRE_ICONO,
			CategoriasSQLite.COLUMNA_NUMERO_SITIOS,
			CategoriasSQLite.COLUMNA_ULTIMA_ACTUALIZACION};

	public CategoriasDataSource(Context context) {
		super(context);
	}

	@Override
	protected SQLiteOpenHelper crearDbHelper(Context context) {
		return new CategoriasSQLite(context);
	}

	public long insertar(CategoriaDTO categoria) {
		ContentValues valores = eventoToObject(categoria);
		return database.insert(CategoriasSQLite.TABLE_NAME, null, valores);
	}
	
	/**
	 * Devuelve la fecha de la ultima actualizacion de la tabla
	 * @return 
	 */
	public long getUltimaActualizacion() {
		String sql = "SELECT MAX(" + CategoriasSQLite.COLUMNA_ULTIMA_ACTUALIZACION + ") FROM " +
				CategoriasSQLite.TABLE_NAME;
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
	public CategoriaDTO getById(long id) {
		CategoriaDTO resul = null;
		String where = CategoriasSQLite.COLUMNA_ID + " = " + id;
		Cursor cursor = database.query(CategoriasSQLite.TABLE_NAME,
				allColumns, where, null, null, null, null);
		cursor.moveToFirst();
	    if(!cursor.isAfterLast()) {
			resul = cursorToObject(cursor);
		}
		cursor.close();
		
		return resul;
	}

	/**
	 * Devuelve una categoria a segun el nombre de la categoria.
	 * @param nombre
	 * @return
	 */
	public CategoriaDTO getByNombre(String nombre) {
		CategoriaDTO resul = null;
		String where = CategoriasSQLite.COLUMNA_NOMBRE + " = '" + nombre + "'";
		Cursor cursor = database.query(CategoriasSQLite.TABLE_NAME,
				allColumns, where, null, null, null, null);
		cursor.moveToFirst();
	    if(!cursor.isAfterLast()) {
			resul = cursorToObject(cursor);
		}
		cursor.close();
		
		return resul;
	}

	/**
	 * Devuelve todas las categorias existentes en la base de datos.
	 * @return
	 */
	public List<CategoriaDTO> getAll() {
		List<CategoriaDTO> resul = new ArrayList<>();

		Cursor cursor = database.query(CategoriasSQLite.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CategoriaDTO categoria = cursorToObject(cursor);

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
	public long actualizar(CategoriaDTO categoria) {
		ContentValues valores = eventoToObject(categoria);
		return database.update(CategoriasSQLite.TABLE_NAME, valores,
				CategoriasSQLite.COLUMNA_ID + "=" + categoria.getId(), null);
	}

	/**
	 * Borra la categoria recibida de la base de datos.
	 * @param categoria
	 */
	public void delete(CategoriaDTO categoria) {
		long id = categoria.getId();
		database.delete(CategoriasSQLite.TABLE_NAME, CategoriasSQLite.COLUMNA_ID
				+ " = " + id, null);
	}

	/**
	 * Convierte un cursor recibido de una consulta a la base de datos en un objeto CategoriaDTO.
	 * @param cursor
	 * @return
	 */
	private CategoriaDTO cursorToObject(Cursor cursor) {
		CategoriaDTO resul = new CategoriaDTO();
		resul.setId(cursor.getLong(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_ID)));
		resul.setNombre(cursor.getString(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_NOMBRE)));
		resul.setDescripcion(cursor.getString(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_DESCRIPCION)));
		resul.setNombreIcono(cursor.getString(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_NOMBRE_ICONO)));
		resul.setNumeroSitios(cursor.getInt(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_NUMERO_SITIOS)));
		long ultimaActualizacion = cursor.getLong(cursor.getColumnIndex(CategoriasSQLite.COLUMNA_ULTIMA_ACTUALIZACION));
		resul.setUltimaActualizacion(new Date(ultimaActualizacion));

		return resul;
	}

	/**
	 * Convierte un objeto categoria en un objeto ContentValues que podra ser usado para insercion/actualizacion
	 * de la base de datos.
	 * @param categoria
	 * @return
	 */
	private ContentValues eventoToObject(CategoriaDTO categoria) {
		ContentValues valores = new ContentValues();
		valores.put(CategoriasSQLite.COLUMNA_ID, categoria.getId());
		valores.put(CategoriasSQLite.COLUMNA_NOMBRE, categoria.getNombre());
		valores.put(CategoriasSQLite.COLUMNA_DESCRIPCION,
				categoria.getDescripcion());
		valores.put(CategoriasSQLite.COLUMNA_NOMBRE_ICONO,
				categoria.getNombreIcono());
		valores.put(CategoriasSQLite.COLUMNA_NUMERO_SITIOS,
				categoria.getNumeroSitios());
		valores.put(CategoriasSQLite.COLUMNA_ULTIMA_ACTUALIZACION, categoria
				.getUltimaActualizacion().getTime());
		return valores;
	}

}