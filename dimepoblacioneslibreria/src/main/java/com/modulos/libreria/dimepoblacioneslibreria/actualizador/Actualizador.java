package com.modulos.libreria.dimepoblacioneslibreria.actualizador;

import android.content.Context;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.AlmacenamientoFactory;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.CategoriasDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.SitiosDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.dimepoblacioneslibreria.excepcion.DimeException;

import java.util.List;

/**
 * Clase que realiza la actualizacion de los datos en la base de datos y almacenamiento externo.
 * Para los nuevos contenidos inserta o actualiza el contenido en la base de datos y para aquellos
 * contenido que tengan imagenes guarda las imagenes en almacenamiento externo.
 * @author h
 *
 */
public class Actualizador {
	private Context contexto;

	public Actualizador(Context contexto) {
		this.contexto = contexto;
	}

	/**
	 * Realiza la insercion/actualizacion de las categorias segun la lista de categorias recibidas.
	 * Ademas almacena la imagen del icono de la categoria.
	 * @param lstCategorias
	 * @throws DimeException
	 */
	public void actualizarCategorias(List<CategoriaDTO> lstCategorias) throws DimeException {
		ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(contexto);
		CategoriasDataSource dataSource = new CategoriasDataSource(contexto);
		try {
			dataSource.open();
			
			Log.d("CATEGORIAS", lstCategorias.toString());
			for(CategoriaDTO categ : lstCategorias) {
				long id = categ.getId();
				CategoriaDTO existente = dataSource.getById(id);
				if(existente == null) {
					dataSource.insertar(categ);
				} else {
					dataSource.actualizar(categ);
				}
				almacenamiento.addIconoCategoria(categ.getIcono(), categ.getNombreIcono(), id);
			}
		} finally {
			dataSource.close();
		}
	}


	/**
	 * Realiza la insercion/actualizacion de los sitios segun la lista de sitios recibidos.
	 * Ademas almacena la imagen del logotipo y las imagenes asociadas a este sitio.
	 * 
	 * @param lstSitios
	 * @throws DimeException
	 */
	public void actualizarSitios(List<SitioDTO> lstSitios) throws DimeException {
		ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(contexto);

		SitiosDataSource dataSource = new SitiosDataSource(contexto);
		try {
			dataSource.open();
			
			Log.d("Sitios", lstSitios.toString());
			for(SitioDTO sitio : lstSitios) {
				long id = sitio.getId();
				SitioDTO existente = dataSource.getById(id);
				if(existente == null) {
					dataSource.insertar(sitio);
				} else {
					dataSource.actualizar(sitio);
				}
				long idSitio = sitio.getId();
				almacenamiento.addImagenSitio(sitio.getLogotipo(), sitio.getNombreLogotipo(), idSitio);
				almacenamiento.addImagenSitio(sitio.getImagen1(), sitio.getNombreImagen1(), idSitio);
				almacenamiento.addImagenSitio(sitio.getImagen2(), sitio.getNombreImagen2(), idSitio);
				almacenamiento.addImagenSitio(sitio.getImagen3(), sitio.getNombreImagen3(), idSitio);
				almacenamiento.addImagenSitio(sitio.getImagen4(), sitio.getNombreImagen4(), idSitio);
			}
		} finally {
			dataSource.close();
		}
	}

}
