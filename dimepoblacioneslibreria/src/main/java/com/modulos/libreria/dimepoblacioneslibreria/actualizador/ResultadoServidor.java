package com.modulos.libreria.dimepoblacioneslibreria.actualizador;

import java.util.List;

/**
 * Created by h on 18/05/16.
 */
public class ResultadoServidor<T> {
    private Long horaServidor;
    private List<T> resultados;

    public ResultadoServidor(Long horaServidor, List<T> resultados) {
        this.horaServidor = horaServidor;
        this.resultados = resultados;
    }

    public Long getHoraServidor() {
        return horaServidor;
    }

    public void setHoraServidor(Long horaServidor) {
        this.horaServidor = horaServidor;
    }

    public List<T> getResultados() {
        return resultados;
    }

    public void setResultados(List<T> resultados) {
        this.resultados = resultados;
    }
}
