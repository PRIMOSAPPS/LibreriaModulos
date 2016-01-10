package com.modulos.libreria.dimepoblacioneslibreria.singleton;

/**
 * Created by h on 10/01/16.
 */
public class SingletonDimePoblaciones {
    private static SingletonDimePoblaciones ourInstance = new SingletonDimePoblaciones();

    private int idLogoPoblacion;
    private int idIconoNotificacion;

    public static SingletonDimePoblaciones getInstance() {
        return ourInstance;
    }

    private SingletonDimePoblaciones() {
    }

    public int getIdLogoPoblacion() {
        return idLogoPoblacion;
    }

    public void setIdLogoPoblacion(int idLogoPoblacion) {
        this.idLogoPoblacion = idLogoPoblacion;
    }

    public int getIdIconoNotificacion() {
        return idIconoNotificacion;
    }

    public void setIdIconoNotificacion(int idIconoNotificacion) {
        this.idIconoNotificacion = idIconoNotificacion;
    }
}
