package com.modulos.libreria.dimepoblacioneslibreria.preferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.CategoriasDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.utilidadeslibreria.preferencias.Preferencias;

import java.util.List;

/**
 * Created by h on 4/10/15.
 */
public class PreferenciasPoblaciones extends Preferencias {

    public final static String PREFIJO_PREFERENCIA_CATEGORIAS = "categoria_";
    public final static String PREFERENCIA_ACTUALIZAR_POR_CATEGORIAS = "pref_actualizar_categorias";

    public PreferenciasPoblaciones(Context contexto) {
        super(contexto);
    }

    /**
     * Devuelve el String que contiene los identificadores de las categorias para actualizar solo por estas categorias.
     * Los identificadores estan separados por comas
     * Si no hay ninguna categoria seleccionada, se devuelve null
     * @return
     */
    public String getActualizacionPorCategorias() {
        String resul = "";
        CategoriasDataSource dataSource = new CategoriasDataSource(contexto);
        try {
            dataSource.open();
            List<CategoriaDTO> lstCategorias = dataSource.getAll();

            SharedPreferences ratePrefs = PreferenceManager
                    .getDefaultSharedPreferences(contexto);
//            boolean actualizarPorCategorias = ratePrefs.getBoolean(PreferenciasActivity.PREFERENCIA_ACTUALIZAR_POR_CATEGORIAS, false);
            boolean actualizarPorCategorias = ratePrefs.getBoolean(PREFERENCIA_ACTUALIZAR_POR_CATEGORIAS, false);
            if(actualizarPorCategorias) {
                for(CategoriaDTO categoria : lstCategorias) {
//                    String key = PreferenciasActivity.PREFIJO_PREFERENCIA_CATEGORIAS + categoria.getId();
                    String key = PREFIJO_PREFERENCIA_CATEGORIAS + categoria.getId();
                    boolean activada = ratePrefs.getBoolean(key, false);
                    if(activada) {
                        resul += ","+categoria.getId();
                    }
                }
            }
            if(!resul.equals("")) {
                resul = resul.substring(1);
            } else {
                resul = null;
            }
        } finally {
            dataSource.close();
        }
        return resul;
    }
}
