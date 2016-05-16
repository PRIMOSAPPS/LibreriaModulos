package com.modulos.libreria.dimepoblacioneslibreria.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.modulos.libreria.dimepoblacioneslibreria.service.ServicioComprobadorActualizaciones;

/**
 * Created by h on 6/03/16.
 */
public class ComprobadorActualizacionesBroadcastReceiver extends BroadcastReceiver {

    public ComprobadorActualizacionesBroadcastReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, ServicioComprobadorActualizaciones.class);
        context.startService(startServiceIntent);
    }
}
