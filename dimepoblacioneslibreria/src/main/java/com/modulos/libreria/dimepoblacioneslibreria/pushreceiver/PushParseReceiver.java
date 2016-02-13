package com.modulos.libreria.dimepoblacioneslibreria.pushreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleNotificacionActivity;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.NotificacionesDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.dimepoblacioneslibreria.preferencias.PreferenciasDime;
import com.modulos.libreria.dimepoblacioneslibreria.singleton.SingletonDimePoblaciones;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PushParseReceiver extends ParsePushBroadcastReceiver {
    private static final String TAG = "PushParseReceiver";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private final static String GRUPO_NOTIFICACIONES_DIME_MONESTERIO = "GRUPO_NOTIFICACIONES_DIME_MONESTERIO";
    private static int idNotificaciones = 0;

	/*
    @Override
	public void onReceive(Context context, Intent intent) {
//		try {
//			Notificacion notificacion = getNotificacion(intent);

			Log.d(TAG, "Recibida una notificacion.");

//			notificacionToBD(context, notificacion);

//			Intent resultIntent = new Intent(context, NotificacionesActivity.class);
//			resultIntent.putExtra(NotificacionesActivity.NOTIFICACION, notificacion);
//            PendingIntent resultPendingIntent =
//                    PendingIntent.getActivity(
//                            context,
//                            0,
//                            resultIntent,
//                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
//                    );
//
//            Log.d(TAG, "Recibida una notificacion: " + notificacion.getTexto());

            long[] patronVibracion = {500};
            //Esto hace posible crear la notificación
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context);

//            Sitio sitio = getSitio(context, notificacion.getIdSitio());

//            NotificationCompat.InboxStyle inboxStyle =
//                    new NotificationCompat.InboxStyle();
//            inboxStyle.setBigContentTitle(sitio.getNombre());
//            Spanned spanTitulo = Html.fromHtml("<b>" + notificacion.getTitulo() + "</b>");
//            inboxStyle.addLine(spanTitulo);
//            Spanned spanTexto = Html.fromHtml("<i>" + notificacion.getTexto() + "</i>");
//            inboxStyle.addLine(spanTexto);

			///////////////////////////////////////////////
			///////////////////////////////////////////////
			///////////////////////////////////////////////
			NotificationCompat.InboxStyle inboxStyle =
					new NotificationCompat.InboxStyle();
			inboxStyle.setBigContentTitle("Nombre");
			Spanned spanTitulo = Html.fromHtml("<b>" + "Titulo" + "</b>");
			inboxStyle.addLine(spanTitulo);
			Spanned spanTexto = Html.fromHtml("<i>" + "notificacion.getTexto()" + "</i>");
			inboxStyle.addLine(spanTexto);
			///////////////////////////////////////////////
			///////////////////////////////////////////////
			///////////////////////////////////////////////

//            inboxStyle.setSummaryText("setSummaryText");
            mBuilder.setStyle(inboxStyle);

//            mBuilder.setSmallIcon(R.drawable.ic_action_notificacion)
//                .setContentTitle(notificacion.getTitulo())
//                .setContentText(notificacion.getTexto())
//                .setContentIntent(resultPendingIntent)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setVibrate(patronVibracion)
//                .setAutoCancel(true);


			///////////////////////////////////////////////
			///////////////////////////////////////////////
			///////////////////////////////////////////////
			mBuilder.setSmallIcon(R.drawable.ic_action_notificacion)
					.setContentTitle("Titulo")
					.setContentText("notificacion.getTexto()")
//					.setContentIntent(resultPendingIntent)
					.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
					.setVibrate(patronVibracion)
					.setAutoCancel(true);
			///////////////////////////////////////////////
			///////////////////////////////////////////////
			///////////////////////////////////////////////


            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = mBuilder.build();
            mNotificationManager.notify(1, notification);
//		} catch (JSONException e) {
//			Log.e(TAG, "JSONException: " + e.getMessage(), e);
//		} catch (ParseException e) {
//			Log.e(TAG, "ParseException: " + e.getMessage(), e);
//		}
	}
	*/


    @Override
    protected void onPushReceive(Context context, Intent intent) {
//        super.onPushReceive(context, intent);
        try {
            NotificacionDTO notificacion = getNotificacion(intent);
            Log.d(TAG, "Recibida una notificacion.");
            notificacionToBD(context, notificacion);

            Intent resultIntent = new Intent(context, DetalleNotificacionActivity.class);
            resultIntent.putExtra(DetalleNotificacionActivity.ID_NOTIFICACION, notificacion.getId());
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
                    );

            Log.d(TAG, "Recibida una notificacion: " + notificacion.getTexto());

            long[] patronVibracion = {500};
            //Esto hace posible crear la notificación
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context);

            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            ///////////////////////////////////////////////

//            inboxStyle.setSummaryText("setSummaryText");

//            mBuilder.setSmallIcon(R.drawable.ic_action_notificacion)
//                .setContentTitle(notificacion.getTitulo())
//                .setContentText(notificacion.getTexto())
//                .setContentIntent(resultPendingIntent)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setVibrate(patronVibracion)
//                .setAutoCancel(true);


            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            int idIconoNotificacion = SingletonDimePoblaciones.getInstance().getIdIconoNotificacion();
            mBuilder.setSmallIcon(idIconoNotificacion)
                    .setContentTitle(notificacion.getTitulo())
					.setContentIntent(resultPendingIntent)
                    .setGroup(GRUPO_NOTIFICACIONES_DIME_MONESTERIO)
                    .setAutoCancel(true);
            PreferenciasDime prefDime = new PreferenciasDime(context);
            if(prefDime.isSonarVibracion()) {
                mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            }
            if(prefDime.isVibrarVibracion()) {
                mBuilder.setVibrate(patronVibracion);
            }

            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            ///////////////////////////////////////////////


            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = mBuilder.build();
            if(idNotificaciones == Integer.MAX_VALUE) {
                idNotificaciones = 0;
            }
            mNotificationManager.notify(idNotificaciones++, notification);


        } catch (JSONException e) {
            Log.e(TAG, "Error json al leer la notificacion.", e);
        } catch (ParseException e) {
            Log.e(TAG, "Error de parse al leer la notificacion.", e);
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        Log.d(TAG, "onPushDismiss()");
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.d(TAG, "onPushOpen()");
        super.onPushOpen(context, intent);
    }

    private NotificacionDTO getNotificacion(Intent intent) throws JSONException, ParseException {
        JSONObject json = new JSONObject(intent.getExtras().getString(
                "com.parse.Data"));
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(json.getLong("id"));
        notificacion.setIdCategoria(json.getLong("idCategoria"));
        notificacion.setTitulo(json.getString("titulo"));
        notificacion.setTexto(json.getString("texto"));
        String strFechaInicio = json.getString("fiv");
        notificacion.setFechaInicioValidez(dateFormat.parse(strFechaInicio));
        String strFechaFin = json.getString("ffv");
        notificacion.setFechaFinValidez(dateFormat.parse(strFechaFin));
        Log.w(TAG, "Recibida notificacion con fecha inicio: " + strFechaInicio + " y fecha de fin: " + strFechaFin);
        Log.w(TAG, "FECHAS PARSEADAS fecha inicio: " + notificacion.getFechaInicioValidez() + " y fecha de fin: " + notificacion.getFechaFinValidez());

        return notificacion;
    }

    private void notificacionToBD(Context context, NotificacionDTO notificacion) {
        NotificacionesDataSource dataSource = new NotificacionesDataSource(context);
        dataSource.open();
        dataSource.insertar(notificacion);
        dataSource.close();
    }
}

