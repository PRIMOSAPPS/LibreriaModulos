package com.modulos.libreria.dimepoblacioneslibreria.preferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.modulos.libreria.utilidadeslibreria.preferencias.Preferencias;

/**
 * Created by h on 8/11/15.
 */
public class PreferenciasDime extends Preferencias {
    public final static String VIBRACION_NOTIFICACION = "vibracion_notificacion";
    public final static String SONIDO_NOTIFICACION = "sonido_notificacion";

    public PreferenciasDime(Context contexto) {
        super(contexto);
    }


    public boolean isSonarVibracion() {
        SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
        return ratePrefs.getBoolean(SONIDO_NOTIFICACION, true);
    }

    public void setSonarVibracion(boolean valor) {
        SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
        SharedPreferences.Editor edit = ratePrefs.edit();
        edit.putBoolean(SONIDO_NOTIFICACION, valor);
        edit.commit();
    }

    public boolean isVibrarVibracion() {
        SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
        return ratePrefs.getBoolean(VIBRACION_NOTIFICACION, true);
    }

    public void setVibrarVibracion(boolean valor) {
        SharedPreferences ratePrefs = PreferenceManager
                .getDefaultSharedPreferences(contexto);
        SharedPreferences.Editor edit = ratePrefs.edit();
        edit.putBoolean(VIBRACION_NOTIFICACION, valor);
        edit.commit();
    }
}
