package com.modulos.libreria.dimepoblacioneslibreria.util;

import android.content.Context;

import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.NotificacionesDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.preferencias.PreferenciasDime;

/**
 * Created by h on 18/05/16.
 */
public class UltimaActualizacion {
    public long getUltimaActualizacionNotificaciones(Context contexto) {
        NotificacionesDataSource dataSource = new NotificacionesDataSource(contexto);
        long ultimaActualizacion = PreferenciasDime.getFechaUltimaComprobacionActualizaciones(contexto);
        if(ultimaActualizacion == 0) {
            dataSource.open();
            ultimaActualizacion = dataSource.getUltimaActualizacion();
            dataSource.close();
        }
        return ultimaActualizacion;
    }
}
