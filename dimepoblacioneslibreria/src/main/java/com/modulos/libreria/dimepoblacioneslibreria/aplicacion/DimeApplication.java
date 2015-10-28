package com.modulos.libreria.dimepoblacioneslibreria.aplicacion;

import android.app.Application;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.actividades.MainActivity;
import com.modulos.libreria.dimepoblacioneslibreria.util.UtilPropiedades;
import com.modulos.libreria.parselibreria.registro.RegistroParse;
import com.modulos.libreria.parselibreria.util.ParsePropiedades;

/**
 * Created by h on 3/10/15.
 */
public class DimeApplication extends Application {

    private final static String TAG = "[DimeApplication]";

    public DimeApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

        UtilPropiedades.getInstance().inicializar(this);
        ParsePropiedades.getInstance().inicializar(this);

        RegistroParse registroParse = new RegistroParse();
        registroParse.registraParse(this, MainActivity.class);
    }

}
