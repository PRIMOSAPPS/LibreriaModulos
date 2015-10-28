package com.modulos.libreria.listadodetallelibreria.adaptadores;

import android.app.Activity;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by h on 8/10/15.
 */
public abstract class ListaAdaptador<T> extends BaseAdapter {
    protected final Activity actividad;
    protected final List<T> listaObjetos;

    public ListaAdaptador(Activity actividad, List<T> listaObjetos) {
        this.actividad = actividad;
        this.listaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        return listaObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

}
