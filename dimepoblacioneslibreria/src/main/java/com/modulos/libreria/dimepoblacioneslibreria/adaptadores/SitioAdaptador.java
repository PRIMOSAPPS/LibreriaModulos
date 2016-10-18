package com.modulos.libreria.dimepoblacioneslibreria.adaptadores;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.modulos.libreria.dimepoblacioneslibreria.R;

import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.AlmacenamientoFactory;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.dimepoblacioneslibreria.singleton.SingletonDimePoblaciones;
import com.modulos.libreria.listadodetallelibreria.adaptadores.ListaAdaptador;

import java.util.List;

/**
 * <b>ESTA CLASE SE CORRESPONDE CON PARTE DE LA VISTA DE LA APLICACION</b>
 * Adaptador del contenido de un SitioDTO a cada View correspondiente al ListView que muestra la lista de sitios. 
 * @author h
 *
 */
public class SitioAdaptador extends ListaAdaptador<SitioDTO> {

	public static class ViewHolder {
		public final ImageView imageView;
		public final TextView textView;
		private SitioDTO sitio;

		public ViewHolder(ImageView imageView, TextView textView) {
			this.imageView = imageView;
			this.textView = textView;
		}

		public SitioDTO getSitio() {
			return sitio;
		}

		public void setSitio(SitioDTO sitio) {
			this.sitio = sitio;
		}
	}

	public SitioAdaptador(Activity actividad, List<SitioDTO> listaSitios) {
		super(actividad, listaSitios);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		ImageView imagen;
		TextView textNombreSitio;
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = actividad.getLayoutInflater();
			convertView = inflater.inflate(R.layout.sitio_view_adapter, null, true);

			imagen = (ImageView) convertView.findViewById(R.id.imagenListaEventos);
			textNombreSitio = (TextView) convertView.findViewById(R.id.textNombreSitio);
			viewHolder = new ViewHolder(imagen, textNombreSitio);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			imagen = viewHolder.imageView;
			textNombreSitio = viewHolder.textView;
		}

		//LayoutInflater inflater = actividad.getLayoutInflater();
		//View view = inflater.inflate(R.layout.sitio_view_adapter, null, true);

		List<SitioDTO> listaSitios = (List<SitioDTO>)listaObjetos;
		SitioDTO sitio = listaSitios.get(position);

		viewHolder.setSitio(sitio);
		//TextView textNombreSitio = (TextView)view.findViewById(R.id.textNombreSitio);
		//ImageView imagen = (ImageView)view.findViewById(R.id.imagenListaEventos);

		textNombreSitio.setText(sitio.getNombre());
		imagen.setImageResource(SingletonDimePoblaciones.getInstance().getIdIconoListaSitios());
//		ItfAlmacenamiento almacenamiento = AlmacenamientoUtilFactory.getAlmacenamiento(actividad);
//		Bitmap bitmap = almacenamiento.getImagenSitio(sitio.getId(), sitio.getNombreLogotipo());
//		imagen.setImageBitmap(bitmap);
		
//		LayoutParams params = new LayoutParams(LayoutParams.fill_parent,
//				15 + (position * 5));
//		view.setLayoutParams(params);
		return convertView;
	}

}
