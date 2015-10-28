package com.modulos.libreria.parselibreria.registro;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.modulos.libreria.parselibreria.util.ParsePropiedades;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by h on 19/10/15.
 */
public class RegistroParse {
    private final static String TAG = "RegistroParse";
    private final static String THREAD_INICIO_PARSE = "ThreadInicioParse";

    public void registraParse(Application application, Class<? extends Activity> classActivity) {
        ParsePropiedades propiedades = ParsePropiedades.getInstance();
        final String applicationId = propiedades.getProperty(ParsePropiedades.PROP_PARSE_APPLICATION_ID);
        final String clientKey = propiedades.getProperty(ParsePropiedades.PROP_PARSE_CLIENT_KEY);
        Log.d(TAG, "applicationId: " + applicationId);
        Log.d(TAG, "clientKey: " + clientKey);

        Parse.initialize(application, applicationId, clientKey);
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParseInstallation.getCurrentInstallation().saveInBackground();
//        PushService.setDefaultPushCallback(application, classActivity);

        /*
        Runnable runableInicioParse = new Runnable() {
            @Override
            public void run() {
                ParseInstallation.getCurrentInstallation().saveInBackground();
                Log.d(TAG, "Registrado applicationId " + applicationId + " y clientKey " + clientKey);
            }
        };
        Thread th = new Thread(runableInicioParse, THREAD_INICIO_PARSE);
        th.start();
        */

//		ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    /*
    public void registraParseMALO() {
        String  androidId = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
        Log.d("VisitaMoralejaApplication", "ANDROID_ID El id conseguido es: " + androidId);
        ParsePropiedades propiedades = ParsePropiedades.getInstance();
        String applicationId = propiedades.getProperty(ParsePropiedades.PROP_PARSE_APPLICATION_ID);
        String clientKey = propiedades.getProperty(ParsePropiedades.PROP_PARSE_CLIENT_KEY);
        Log.d("VisitaMoralejaApplication", "applicationId: " + applicationId);
        Log.d("VisitaMoralejaApplication", "clientKey: " + clientKey);

        Parse.initialize(this, applicationId, clientKey);
        PushService.setDefaultPushCallback(this, MainActivity.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("UniqueId", androidId);
        installation.setObjectId(null);
        installation.saveInBackground();
    }
    */
}
