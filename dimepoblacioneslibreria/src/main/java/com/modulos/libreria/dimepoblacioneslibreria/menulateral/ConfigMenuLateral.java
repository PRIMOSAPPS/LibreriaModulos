package com.modulos.libreria.dimepoblacioneslibreria.menulateral;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.modulos.libreria.buzonciudadanolibreria.BuzonCiudadanoActivity;
import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.ListaNotificacionesActivity;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.ListaSitiosActivity;
import com.modulos.libreria.dimepoblacioneslibreria.adaptadores.MenuLateralAdaptador;
import com.modulos.libreria.dimepoblacioneslibreria.constantes.Constantes;
import com.modulos.libreria.dimepoblacioneslibreria.util.Propiedades;
import com.modulos.libreria.radiolibreria.StreamPlayerActivity;
import com.modulos.libreria.utilidadeslibreria.menulateral.DatosItemMenuLateral;
import com.modulos.libreria.utilidadeslibreria.menulateral.IConfigMenuLateral;
import com.modulos.libreria.utilidadeslibreria.util.UtilPropiedades;

import java.util.ArrayList;
import java.util.List;

public class ConfigMenuLateral implements IConfigMenuLateral {
	private static int IND_PUNTOS_INTERES = 0;
	private static int IND_COLABORACION_CIUDADANA = 1;
	private static int IND_BANDOS = 2;
	private static int IND_RADIO = 3;

	public ConfigMenuLateral() {
		super();
	}

	public void configurarMenuLateral(final Activity actividad, int idLinearLayoutMenuLateral, int idDrawerLayout) {

		List<DatosItemMenuLateral> listaItemsMenu = new ArrayList<>();

		DatosItemMenuLateral datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_puntos_interes, R.mipmap.puntos_de_interes);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_colaboracion_ciudadana, R.mipmap.colaboracion_ciudadana);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_bandos, R.mipmap.bandos);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_radio, R.mipmap.radio_monesterio);
		listaItemsMenu.add(datosItem);

		ListView mDrawerOptions = (ListView) actividad.findViewById(idLinearLayoutMenuLateral);
		final DrawerLayout mDrawer = (DrawerLayout) actividad.findViewById(idDrawerLayout);
		mDrawerOptions.setAdapter(new MenuLateralAdaptador(actividad, listaItemsMenu));

		mDrawerOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int indice,
									long arg3) {
				if (indice == IND_RADIO) {
					Intent i = new Intent(actividad, StreamPlayerActivity.class);
					String urlRadio = UtilPropiedades.getInstance().getProperty(Propiedades.PROP_URL_RADIO);
					i.putExtra(StreamPlayerActivity.URL_RADIO, urlRadio);
					actividad.startActivity(i);
				} else if (indice == IND_PUNTOS_INTERES) {
					Intent i = new Intent(actividad, ListaSitiosActivity.class);
					i.putExtra(Constantes.categoria, Constantes.SITIO);
					actividad.startActivity(i);
				} else if (indice == IND_COLABORACION_CIUDADANA) {
					Intent i = new Intent(actividad, BuzonCiudadanoActivity.class);
					//i.putExtra(BuzonCiudadanoActivity.DIRECTORIO, Environment.DIRECTORY_PICTURES);
					actividad.startActivity(i);
				} else if (indice == IND_BANDOS) {
					Intent i = new Intent(actividad, ListaNotificacionesActivity.class);
					actividad.startActivity(i);
				}
				mDrawer.closeDrawers();
			}
		});
	}

}
