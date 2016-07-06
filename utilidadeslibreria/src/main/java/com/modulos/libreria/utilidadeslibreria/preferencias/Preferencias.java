package com.modulos.libreria.utilidadeslibreria.preferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferencias {
	private final static String PREFERENCIA_ACTUALIZAR_AUTOMATICAMENTE = "pref_actualizar_automaticamente";
	private final static String ALMACENAMIENTO = "tipoAlmacenamiento";
	public final static String PRIMER_ARRANQUE = "primerArranque";
	private final static String ALMACENAMIENTO_INTERNO = "almacenamientoInterno";
	private final static String ALMACENAMIENTO_EXTERNO = "almacenamientoExterno";

	private final static String TAG = Preferencias.class.getName();

	protected Context contexto;

	public Preferencias(Context contexto) {
		this.contexto = contexto;
	}

	public boolean isPrimerArranque() {
		return isPrimerArranque(contexto);
	}

	public boolean isPrimerArranque(Context contexto) {
		Log.d(TAG, "Se comprueba el primer arranque.");
		
		SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
		return ratePrefs.getBoolean(PRIMER_ARRANQUE, true);
	}

	public void marcarPrimarArranqueRealizado() {
		marcarPrimarArranqueRealizado(contexto);
	}

	public void marcarPrimarArranqueRealizado(Context contexto) {
		SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
        Editor edit = ratePrefs.edit();
        edit.putBoolean(PRIMER_ARRANQUE, false);
        edit.commit();
	}

	public void asignarModoAlmacenamiento() {
		asignarModoAlmacenamiento(contexto);
	}

	public void asignarModoAlmacenamiento(Context contexto) {
		SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
		Editor edit = ratePrefs.edit();
		
		Log.d(TAG, "Comprobando el tipo de almacenamiento");
        String estadoSD = Environment.getExternalStorageState();
        
        Log.d(TAG, "Estado del almacenamiento externo: " + estadoSD);
        String almacenamiento = ALMACENAMIENTO_INTERNO;
        if(estadoSD.equals(Environment.MEDIA_MOUNTED)) {
        	almacenamiento = ALMACENAMIENTO_EXTERNO;
        }
        
        Log.d(TAG, "Almacenamiento asignado: " + almacenamiento);
        edit.putString(ALMACENAMIENTO, almacenamiento);

        edit.commit();
	}

	public String getTipoAlmacenamiento() {
		return getTipoAlmacenamiento(contexto);
	}

	public String getTipoAlmacenamiento(Context contexto) {
		SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
		return ratePrefs.getString(ALMACENAMIENTO, ALMACENAMIENTO_INTERNO);
	}

	public boolean actualizacionAutomatica() {
		return actualizacionAutomatica(contexto);
	}

	public boolean actualizacionAutomatica(Context contexto) {
		Log.w(TAG, "Se comprueba si estan activadas las actualizaciones automaticas.");
		
		SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
		return ratePrefs.getBoolean(PREFERENCIA_ACTUALIZAR_AUTOMATICAMENTE, true);
	}

	public boolean isAlmacenamientoInterno() {
		return isAlmacenamientoInterno(contexto);
	}

	public boolean isAlmacenamientoInterno(Context contexto) {
		String tipoAlmacenamiento = getTipoAlmacenamiento();
		return ALMACENAMIENTO_INTERNO.equals(tipoAlmacenamiento);
	}
}
