package com.modulos.libreria.utilidadeslibreria.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by h on 30/09/15.
 */
public class Gps implements LocationListener {
    public interface GpsListener {
        void actualizacionPosicion(Location locationActual);
    }
    private static final long TIEMPO_MIN_ACTUALIZAR_LOCATION = 10 * 1000; // 10 segundos
    private static final long DISTANCIA_MIN_DISTANCIA_MIN = 5; // 5 metros

    public final static String COCHE = "driving";
    public final static String BICI = "bicycling";
    public final static String ANDANDO = "walking";

    /**
     * Tiempo umbral para considerar que dos localizaciones consecutivas se pueden considerar como
     * indicaciones correctas para una misma localizacion, o relativamente cerca si se esta en
     * movimiento.
     */
    private final static long TIEMPO_UMBRAL = 120 * 1000;
    private final static String TAG = "[Gps]";

    /**
     * Ultima localizacion recibida, <b>no tiene por que estar confirmada</b>
     */
    private Location ultimaLocalizacion;
    /**
     * Numero de localizaciones a recibir antes de asegurar que se ha producido la localizacion.
     * Esto se debe a que la ultima localizacion que devuelve android puede ser bastante anterior
     */
    private int numLocalizacionesNecesarias;
    /**
     * Numero de localizaciones recibidas consecutivas en las que la separacion entre dos consecutivas
     * estan dentro del umbral permitido.
     */
    private int numLocalizacionesRecibidas;
    /**
     * Hora de la ultima localizacion recibida, si se supera el umbral configurado no se tendra en
     * cuenta para aumentar las localizaciones recibidas.
     */
    private Calendar horaUltimaLocalizacion;
    /**
     * Tiempo umbral para considerar que dos localizaciones consecutivas se pueden considerar como
     * indicaciones correctas para una misma localizacion, o relativamente cerca si se esta en
     * movimiento.
     */
    private long tiempoUmbral;

    private long tiempoMinimoRegistroLocalizacion;
    private long distanciaMinimaRegistroLocalizacion;

    private Activity actividadContext;

    private LocationManager locationManager;

    private String[] proveedores = {LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER, LocationManager.PASSIVE_PROVIDER};

    private List<GpsListener> lstListeners = new ArrayList<>();

    public Gps(Activity actividadContext) {
        this(actividadContext, 1);
    }

    public Gps(Activity actividadContext, int numLocalizacionesNecesarias) {
        this.actividadContext = actividadContext;
        this.numLocalizacionesNecesarias = numLocalizacionesNecesarias;
        tiempoMinimoRegistroLocalizacion = TIEMPO_MIN_ACTUALIZAR_LOCATION;
        distanciaMinimaRegistroLocalizacion = DISTANCIA_MIN_DISTANCIA_MIN;
        tiempoUmbral = TIEMPO_UMBRAL;

        locationManager = (LocationManager)actividadContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        registrarLocationManager();
    }

    @Override
    public void onLocationChanged(Location location) {
        ultimaLocalizacion = location;

        Calendar calActual = Calendar.getInstance();
        if(horaUltimaLocalizacion == null) {
            horaUltimaLocalizacion = calActual;
        }
        long timeMillisUltimaLoc = horaUltimaLocalizacion.getTimeInMillis();
        long timeMillisActual = calActual.getTimeInMillis();
        Log.v(TAG, "Cambio en la localizacion: " + (timeMillisActual - timeMillisUltimaLoc) + " -- " + (timeMillisActual - timeMillisUltimaLoc <= tiempoUmbral) +
                numLocalizacionesRecibidas + " -- " + numLocalizacionesNecesarias);
        if (timeMillisActual - timeMillisUltimaLoc <= tiempoUmbral) {
            if (numLocalizacionesRecibidas < numLocalizacionesNecesarias) {
                numLocalizacionesRecibidas++;
            } else {
                Log.v(TAG, "Se ha actualizado a una localizacion considerada correcta.");
                publicarLocalizacion();
            }
        } else {
            numLocalizacionesRecibidas = 0;
        }
        horaUltimaLocalizacion = calActual;

        String strMsj = "Localizacion cambiada: Latitud: {0}, longitud: {1} desde el proveedor {3}";
        String strMsjFromateado = MessageFormat.format(strMsj, ultimaLocalizacion.getLatitude(), ultimaLocalizacion.getLongitude(), ultimaLocalizacion.getProvider());
        Log.d(TAG, strMsjFromateado);
    }

    public void pausarRegistro() {
        for(String proveedor : proveedores) {
            locationManager.removeUpdates(this);
        }
    }

    public void registrarLocationManager() {
        for(String proveedor : proveedores) {
            locationManager.requestLocationUpdates(proveedor, TIEMPO_MIN_ACTUALIZAR_LOCATION, DISTANCIA_MIN_DISTANCIA_MIN, this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setTiempoUmbral(long tiempoUmbral) {
        this.tiempoUmbral = tiempoUmbral;
    }

    public void setNumLocalizacionesNecesarias(int numLocalizacionesNecesarias) {
        this.numLocalizacionesNecesarias = numLocalizacionesNecesarias;
    }

    public void setTiempoMinimoRegistroLocalizacion(long tiempoMinimoRegistroLocalizacion) {
        this.tiempoMinimoRegistroLocalizacion = tiempoMinimoRegistroLocalizacion;
    }

    public void setDistanciaMinimaRegistroLocalizacion(long distanciaMinimaRegistroLocalizacion) {
        this.distanciaMinimaRegistroLocalizacion = distanciaMinimaRegistroLocalizacion;
    }

    public void registrarLocationManager(GpsListener listener) {
        lstListeners.add(listener);
    }

    public void deregistrar(GpsListener listener) {
        lstListeners.remove(listener);
    }

    private void publicarLocalizacion() {
        for(GpsListener listener : lstListeners) {
            listener.actualizacionPosicion(ultimaLocalizacion);
        }
    }

}
