package com.modulos.libreria.dimepoblacioneslibreria.adaptadores;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.utilidadeslibreria.menulateral.DatosItemMenuLateral;

import java.util.List;

public class MenuLateralAdaptador extends BaseAdapter {
	private final Activity actividad;
	private final List<DatosItemMenuLateral> listaItems;

	private static class ViewHolder {
		public final ImageView imageView;
		public final TextView textView;

		public ViewHolder(ImageView imageView, TextView textView) {
			this.imageView = imageView;
			this.textView = textView;
		}
	}
	
	public MenuLateralAdaptador(Activity actividad,
								List<DatosItemMenuLateral> listaItems) {
		super();
		this.actividad = actividad;
		this.listaItems = listaItems;
	}

	@Override
	public int getCount() {
		return listaItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listaItems.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageView;
		TextView textView;
		if (convertView == null) {
			LayoutInflater inflater = actividad.getLayoutInflater();
			convertView = inflater.inflate(R.layout.opcion_menu_lateral, null, true);

			imageView = (ImageView) convertView.findViewById(R.id.imagen_menu_lateral);
			textView = (TextView) convertView.findViewById(R.id.titulo_menu_lateral);
			convertView.setTag(new ViewHolder(imageView, textView));
		} else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			imageView = viewHolder.imageView;
			textView = viewHolder.textView;
		}

		DatosItemMenuLateral datosItem = listaItems.get(position);
		textView.setText(datosItem.getTextoMenu());
		Resources resources = actividad.getResources();
//		int identificadorImagen = resources.getIdentifier(datosItem.getNombreIcono(), "drawable", actividad.getPackageName());
		int identificadorImagen = datosItem.getIdentificadorIcono();
		Drawable drawable = resources.getDrawable(identificadorImagen);
		imageView.setImageDrawable(drawable);
		return convertView;
	}

}
