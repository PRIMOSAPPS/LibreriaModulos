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

	public SitioAdaptador(Activity actividad, List<SitioDTO> listaSitios) {
		super(actividad, listaSitios);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(R.layout.sitio_view_adapter, null, true);

		List<SitioDTO> listaSitios = (List<SitioDTO>)listaObjetos;
		SitioDTO sitio = listaSitios.get(position);
		
		view.setTag(sitio);
        TextView textNombreSitio = (TextView)view.findViewById(R.id.textNombreSitio);
		textNombreSitio.setText(sitio.getNombre());
		
		ImageView imagen = (ImageView)view.findViewById(R.id.imagenListaEventos);
		imagen.setImageResource(SingletonDimePoblaciones.getInstance().getIdIconoListaSitios());
//		ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(actividad);
//		Bitmap bitmap = almacenamiento.getImagenSitio(sitio.getId(), sitio.getNombreLogotipo());
//		imagen.setImageBitmap(bitmap);
		
//		LayoutParams params = new LayoutParams(LayoutParams.fill_parent,
//				15 + (position * 5));
//		view.setLayoutParams(params);
		return view;
	}

}
