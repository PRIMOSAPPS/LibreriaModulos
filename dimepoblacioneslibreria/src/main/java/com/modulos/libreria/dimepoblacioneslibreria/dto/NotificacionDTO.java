package com.modulos.libreria.dimepoblacioneslibreria.dto;

import com.modulos.libreria.utilidadeslibreria.util.UtilFechas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by h on 1/11/15.
 */
public class NotificacionDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8881102854923668978L;

    private long id;
    private long idCategoria;
    private String titulo;
    private String texto;
    private Date fechaInicioValidez;
    private Date fechaFinValidez;
    private Date ultimaActualizacion;

    public NotificacionDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaInicioValidez() {
        return fechaInicioValidez;
    }

    public void setFechaInicioValidez(Date fechaInicioValidez) {
        this.fechaInicioValidez = fechaInicioValidez;
    }

    public Date getFechaFinValidez() {
        return fechaFinValidez;
    }

    public void setFechaFinValidez(Date fechaFinValidez) {
        this.fechaFinValidez = fechaFinValidez;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public boolean isActiva() {
        Date inicio = getFechaInicioValidez();
        Date fin = getFechaFinValidez();
        return UtilFechas.isActivaFechaActual(inicio, fin);
    }


}
