package com.modulos.libreria.dimepoblacioneslibreria.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.actualizador.ConectorServidor;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.NotificacionesDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.dimepoblacioneslibreria.excepcion.DimeException;
import com.modulos.libreria.dimepoblacioneslibreria.preferencias.PreferenciasDime;
import com.modulos.libreria.dimepoblacioneslibreria.pushreceiver.NotificationFactory;
import com.modulos.libreria.utilidadeslibreria.util.UtilConexion;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;

/**
 * Created by h on 6/03/16.
 */
public abstract class ServicioComprobadorActualizaciones extends Service {
    private final static String GRUPO_NOTIFICACIONES_ACTUALIZACIONES_VISITA_MORALEJA = "GRUPO_NOTIFICACIONES_ACTUALIZACIONES_VISITA_MORALEJA";
    // constant
    private final  static String TAG = "[ServicioComprobadorAc]";



    // run on another Thread to avoid crash
    private Handler mHandler = null;
    // timer handling
    private Timer mTimer = null;

    protected abstract Class getClassNotificacion();

    public ServicioComprobadorActualizaciones() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mHandler == null) {
            mHandler = new Handler();
            mHandler.postDelayed(new RunnableComprobador(), ComprobadorActualizacionNotificaciones.INTERVALO_EJECUCION);
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    class RunnableComprobador implements Runnable {
        public void run() {
            Log.d(TAG, "RunnableComprobador.run()");
            ThreadComprobador tc = new ThreadComprobador(ServicioComprobadorActualizaciones.this);
            tc.start();
            mHandler.postDelayed(this, ComprobadorActualizacionNotificaciones.INTERVALO_EJECUCION);
        }
    }

    class ThreadComprobador extends Thread {
        private Context contexto;

        ThreadComprobador(Context contexto) {
            super("ServicioComprobadorActualizaciones");
            this.contexto = contexto;
        }

        @Override
        public void run() {
            Log.d(TAG, "ThreadComprobador.run()");
            ComprobadorActualizacionNotificaciones comprobador = new ComprobadorActualizacionNotificaciones(contexto, getClassNotificacion());
            comprobador.comprobar();
        }

    }

}

