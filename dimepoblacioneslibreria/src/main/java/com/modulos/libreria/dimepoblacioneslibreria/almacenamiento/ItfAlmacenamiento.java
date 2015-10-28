package com.modulos.libreria.dimepoblacioneslibreria.almacenamiento;

import android.graphics.Bitmap;

public interface ItfAlmacenamiento {

	/**
	 * Devuelve la imagen que pertenece a una categoria con identificador idCategoria y nombre
	 * @param idCategoria
	 * @param nombre
	 * @return
	 */
	public abstract Bitmap getIconoCategoria(long idCategoria, String nombre);

	/**
	 * Devuelve la imagen que pertenece a un sitio con identificador idSitio y nombre
	 * 
	 * @param idSitio
	 * @param nombre
	 * @return
	 */
	public abstract Bitmap getImagenSitio(long idSitio, String nombre);

	/**
	 * Aniade una imagen que pertenece a una categoria al almacenamiento externo cuyo nombre es nombreImagen y pertenece
	 * a una categoria con identificador idCategoria
	 * @param imagen
	 * @param nombreImagen
	 * @param idCategoria
	 */
	public abstract void addIconoCategoria(Bitmap imagen, String nombreImagen,
										   long idCategoria);

	/**
	 * Aniade una imagen que pertenece a un sitio al almacenamiento externo cuyo nombre es nombreImagen y pertenece
	 * a un sitio con identificador idSitio
	 * 
	 * @param imagen
	 * @param nombreImagen
	 * @param idSitio
	 */
	public abstract void addImagenSitio(Bitmap imagen, String nombreImagen,
										long idSitio);

}