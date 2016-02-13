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
import com.modulos.libreria.dimepoblacioneslibreria.actividades.PreferenciasActivity;
import com.modulos.libreria.dimepoblacioneslibreria.adaptadores.MenuLateralAdaptador;
import com.modulos.libreria.dimepoblacioneslibreria.constantes.Constantes;
import com.modulos.libreria.dimepoblacioneslibreria.util.UtilPropiedades;
import com.modulos.libreria.radiolibreria.StreamPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class ConfigMenuLateral {
	private static int IND_RADIO = 0;
	private static int IND_PUNTOS_INTERES = 1;
	private static int IND_COLABORACION_CIUDADANA = 2;
	private static int IND_BANDOS = 3;
	private static int IND_PREFERENCIAS = 4;
	
	private Activity actividad;

	public ConfigMenuLateral(Activity actividad) {
		super();
		this.actividad = actividad;
	}

	public void iniciarMenuLateral() {

		List<DatosItemMenuLateral> listaItemsMenu = new ArrayList<>();

		DatosItemMenuLateral datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_radio, R.mipmap.ml_home_radio_monesterio);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_puntos_interes, R.mipmap.ml_home_puntos_interes);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_colaboracion_ciudadana, R.mipmap.ml_home_colaboracion_ciudadana);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.lib_dime_lbl_bandos, R.mipmap.ml_home_bandos_moviles);
		listaItemsMenu.add(datosItem);

		datosItem = new DatosItemMenuLateral(R.string.action_settings, android.R.drawable.ic_menu_manage);
		listaItemsMenu.add(datosItem);

		ListView mDrawerOptions = (ListView) actividad.findViewById(R.id.listMenuLateral);
		final DrawerLayout mDrawer = (DrawerLayout) actividad.findViewById(R.id.drawer_layout);
		mDrawerOptions.setAdapter(new MenuLateralAdaptador(actividad, listaItemsMenu));

		mDrawerOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int indice,
									long arg3) {
				if (indice == IND_RADIO) {
					Intent i = new Intent(actividad, StreamPlayerActivity.class);
					String urlRadio = UtilPropiedades.getInstance().getProperty(UtilPropiedades.PROP_URL_RADIO);
					i.putExtra(StreamPlayerActivity.URL_RADIO, urlRadio);
					actividad.startActivity(i);
				} else if (indice == IND_PUNTOS_INTERES) {
					Intent i = new Intent(actividad, ListaSitiosActivity.class);
					i.putExtra(Constantes.categoria, Constantes.SITIO);
					actividad.startActivity(i);
				} else if (indice == IND_COLABORACION_CIUDADANA) {
					Intent i = new Intent(actividad, BuzonCiudadanoActivity.class);
					i.putExtra(BuzonCiudadanoActivity.DIRECTORIO, Environment.DIRECTORY_PICTURES);
					actividad.startActivity(i);
				} else if (indice == IND_BANDOS) {
					Intent i = new Intent(actividad, ListaNotificacionesActivity.class);
					actividad.startActivity(i);
				} else if (indice == IND_PREFERENCIAS) {
					Intent i = new Intent(actividad, PreferenciasActivity.class);
					actividad.startActivity(i);
				}
				mDrawer.closeDrawers();
			}
		});
	}

}
