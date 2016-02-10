package com.modulos.libreria.utilidadeslibreria.util;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Created by h on 11/10/15.
 */
public class GoogleMaps {
    private final static String URL_MAPS_GOOGLE_APIS_JSON = "http://maps.googleapis.com/maps/api/directions/json";
    // https://developers.google.com/maps/documentation/geocoding/intro
    private final static String URL_MAPS_GOOGLE_GEOCODE = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private final static String AMP_PARAM_ORIGIN = "?origin=";
    private final static String AMP_PARAM_DSETINATION = "&destination=";
    private final static String TAG = "[GoogleMaps]";

    public static interface GoogleMapsGeocodeListener {

//        void tratarJson(String json);
        void direccionFromLocation(String direccion);
    }

    /**
	 * Clase qe realiza la llamada al API de google para conseguir losdatos de la ruta en formato JSON.
	 * @author h
	 *
	 */
	private class CalcRutaAsyncTaskGetDireccion extends AsyncTask<Void, Void, String> {
        //	    private ProgressDialog progressDialog;
        private String url;
        private GoogleMapsGeocodeListener listener;

        CalcRutaAsyncTaskGetDireccion(String url, GoogleMapsGeocodeListener listener) {
            this.url = url;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... params) {
            String resul = null;
            if (url != null) {
                JSONParser jParser = new JSONParser();
                resul = jParser.getJSONFromUrl(url);


            }
            return resul;
        }

        @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
            if(result != null) {
                try {
                    String direccion = getDireccion(result);
                    listener.direccionFromLocation(direccion);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
	    }


        private String getDireccion(String strJson) throws JSONException {
            JSONObject json = new JSONObject(strJson);
            JSONArray resultsArray = json.getJSONArray("results");
            JSONObject obj0 = resultsArray.getJSONObject(0);
            return obj0.getString("formatted_address");
        }
    }


    public void conseguirDireccion(Location location, GoogleMapsGeocodeListener listener) {
        // https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452

        if(listener != null) {
            String strUrl = URL_MAPS_GOOGLE_GEOCODE + location.getLatitude() + "," + location.getLongitude();
            CalcRutaAsyncTaskGetDireccion async = new CalcRutaAsyncTaskGetDireccion(strUrl, listener);
            async.execute((Void) null);
        }
    }

    public Intent getUrlMapsApps(Double latitud, Double longitud, String etiqueta, Integer zoom) {
        return getUrlMapsApps(latitud, longitud, zoom, etiqueta, "");
    }

    public Intent getUrlMapsApps(Double latitud, Double longitud, Integer zoom, String etiqueta, String query) {
        String paqueteMapa = "com.google.android.apps.maps";
        String parteUrlPatron = "geo:0,0?q={0,number,###.#######},{1,number,###.#######}({2})&z={3}&mid=zUB3AjgrW0uo.k54AXa3v4U6I";

        MessageFormat msgFormat = new MessageFormat(parteUrlPatron);
        msgFormat.setLocale(Locale.US);
        Object[] arrParams = {latitud, longitud, etiqueta, zoom};
        String parteUrl = msgFormat.format(arrParams);
        if(query != null && !query.equals("")) {
            parteUrl = parteUrl + "&" + query;
        }
        Uri gmmIntentURi = Uri.parse(parteUrl);

        Intent resul = new Intent(Intent.ACTION_VIEW, gmmIntentURi);
        resul.setPackage(paqueteMapa);

        return resul;
    }
}
