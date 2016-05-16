package com.modulos.libreria.dimepoblacioneslibreria.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
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

/**
 * Created by h on 14/05/16.
 */
public class ComprobadorActualizacionNotificaciones {
    private final  static String TAG = "[ComprActualNotificaci]";

    public static final long INTERVALO_EJECUCION = 15 * 60 * 1000;
    //public static final long INTERVALO_EJECUCION = 30 * 1000;

    private Context contexto;
    private Class classNotificacion;

    private NotificacionesDataSource dataSource;

    public ComprobadorActualizacionNotificaciones(Context contexto, Class classNotificacion) {
        this.contexto = contexto;
        this.classNotificacion = classNotificacion;
        dataSource = new NotificacionesDataSource(contexto);
    }

    public void comprobar() {
        long ahora = Calendar.getInstance().getTimeInMillis();
        if (intervaloCumplido(ahora) && UtilConexion.estaConectado(contexto)) {
            realizarComprobacion();
            PreferenciasDime.setFechaUltimaComprobacionActualizaciones(contexto, ahora);
        }
    }

    private long getUltimaActualizacionNotificaciones() {
        long ultimaActualizacion = PreferenciasDime.getFechaUltimaComprobacionActualizaciones(contexto);
        if(ultimaActualizacion == 0) {
            dataSource.open();
            ultimaActualizacion = dataSource.getUltimaActualizacion();
            dataSource.close();
        }
        return ultimaActualizacion;
    }

    private void realizarComprobacion() {

        ConectorServidor cs = new ConectorServidor(contexto);
        try {
            dataSource.open();
            long ultimaActualizacion = getUltimaActualizacionNotificaciones();
            List<NotificacionDTO> lstActualizables = cs.getListaNotificacionesActualizables(ultimaActualizacion);
            if(!lstActualizables.isEmpty()) {
                for(NotificacionDTO notificacion : lstActualizables) {
                    List<NotificacionDTO> lstCompletas = cs.getNotificacion(notificacion);
                    if(!lstCompletas.isEmpty()) {
                        notificacion = lstCompletas.get(0);

                        NotificacionDTO notificacionBD = dataSource.getById(notificacion.getId());
                        if(notificacionBD == null) {
                            dataSource.insertar(notificacion);

                            NotificationFactory notifFact = new NotificationFactory();
                            android.support.v7.app.NotificationCompat.Builder mBuilder = notifFact.crearNotificationBuilder(contexto, notificacion, classNotificacion);

                            NotificationManager mNotificationManager =
                                    (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
                            Notification notification = mBuilder.build();
                            mNotificationManager.notify((int) notificacion.getId(), notification);
                        } else {
                            dataSource.actualizar(notificacion);
                        }
                    }
                }
            }
        } catch (DimeException e) {
            Log.e(TAG, "Error al consultar la lista de notificaciones actualizables para comprobar actualizaciones.", e);
        } finally {
            dataSource.close();
        }
    }

    /**
     * Comprueba si el tiempo transcurrido entre la ultima actualizacion y el tiempo recibido supera el intervalo
     * @param tiempo
     * @return
     */
    private boolean intervaloCumplido(long tiempo) {
        long ultimaComprobacion = PreferenciasDime.getFechaUltimaComprobacionActualizaciones(contexto);
        return ultimaComprobacion + INTERVALO_EJECUCION <= tiempo;
    }
}
