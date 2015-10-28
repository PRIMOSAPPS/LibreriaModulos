package com.modulos.libreria.dimepoblacioneslibreria.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleSitioActivity;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.AlmacenamientoFactory;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;

/**
 * <b>ESTA CLASE SE CORRESPONDE CON PARTE DE LA VISTA DE LA APLICACION</b>
 * Adaptador para las imagenes de los sitios que son mostradas en la galeria en la actividad <b>DetalleSitioActivity</b>
 * Con este adaptador se muestran las imagenes en la galeria.
 * 
 * @author h
 *
 */
public class ImageAdapter extends BaseAdapter {
	/**
	 * 
	 */
	private final DetalleSitioActivity ImageAdapter;
	/** The parent context */
	private Context myContext;
	private SitioDTO sitio;

	/** Simple Constructor saving the 'parent' context. */
	public ImageAdapter(DetalleSitioActivity detalleEventoActivity, Context c, SitioDTO sitio) {
		ImageAdapter = detalleEventoActivity;
		this.myContext = c;
		this.sitio = sitio;
	}
	
	private boolean noVacio(String str) {
		return str != null && !str.equals("");
	}

	/**
	 * Devuelve el numero de imagenes que existen en el objeto SitioDTO
	 */
	public int getCount() {
		int resul = 0;
		if(noVacio(sitio.getNombreImagen1())) {
			resul++;
		}
		if(noVacio(sitio.getNombreImagen2())) {
			resul++;
		}
		if(noVacio(sitio.getNombreImagen3())) {
			resul++;
		}
		if(noVacio(sitio.getNombreImagen4())) {
			resul++;
		}
		return resul;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Devuelve el nombre de la imagen segun la posicion pedida. Necesario para poder leer el fichero que contiene la imagen
	 * en el almacenamiento.
	 * @param position
	 * @return
	 */
	private String getNombreImagen(int position) {
		switch (position) {
		case 0:
			return sitio.getNombreImagen1();
		case 1:
			return sitio.getNombreImagen2();
		case 2:
			return sitio.getNombreImagen3();
		case 3:
			return sitio.getNombreImagen4();
		default:
			return null;
		}
		
	}

	/**
	 * Devueleve un ImageView para ser mostrado en la galeria.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get a View to display image data
		ImageView iv = new ImageView(this.myContext);
		ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(myContext);
		Bitmap bmp = almacenamiento.getImagenSitio(sitio.getId(), getNombreImagen(position));
		iv.setImageBitmap(bmp);
		iv.setTag(bmp);
		iv.setScaleType(ImageView.ScaleType.FIT_END);

		return iv;
	}
}