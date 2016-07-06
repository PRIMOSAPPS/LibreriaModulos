package com.modulos.libreria.dimepoblacioneslibreria.pushreceiver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleNotificacionActivity;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.dimepoblacioneslibreria.preferencias.PreferenciasDime;
import com.modulos.libreria.dimepoblacioneslibreria.singleton.SingletonDimePoblaciones;

/**
 * Created by h on 13/05/16.
 */
public class NotificationFactory {
    private final static String GRUPO_NOTIFICACIONES_DIME_MONESTERIO = "GRUPO_NOTIFICACIONES_DIME_MONESTERIO";
    private final static long[] PATRON_VIBRACION = {1000, 1000};

    public NotificationCompat.Builder crearNotificationBuilder(Context context, NotificacionDTO notificacion, Class clase) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context);

        Intent resultIntent = createResultIntent(context, clase);
        resultIntent.putExtra(DetalleNotificacionActivity.ID_NOTIFICACION, notificacion.getId());
        int requestCode = (int)notificacion.getId();
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        requestCode,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        int idIconoNotificacion = SingletonDimePoblaciones.getInstance().getIdIconoNotificacion();
        mBuilder.setSmallIcon(idIconoNotificacion)
                .setContentTitle(notificacion.getTitulo())
                .setContentIntent(resultPendingIntent)
                .setGroup(GRUPO_NOTIFICACIONES_DIME_MONESTERIO)
                .setGroupSummary(true)
                .setAutoCancel(true);
        PreferenciasDime prefDime = new PreferenciasDime(context);
        if(prefDime.isSonarVibracion()) {
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }
        if(prefDime.isVibrarVibracion()) {
            mBuilder.setVibrate(PATRON_VIBRACION);
        }
        if(prefDime.isLedRecibirNotificacion(context)) {
            mBuilder.setLights(Color.YELLOW, 2000, 1500);
        }

        return mBuilder;
    }

    protected Intent createResultIntent(Context context, Class clase) {
        Intent resultIntent = new Intent(context, clase);
        return resultIntent;
    }
}
