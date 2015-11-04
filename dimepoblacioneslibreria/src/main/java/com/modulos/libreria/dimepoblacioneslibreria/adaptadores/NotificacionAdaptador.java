package com.modulos.libreria.dimepoblacioneslibreria.adaptadores;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.AlmacenamientoFactory;
import com.modulos.libreria.dimepoblacioneslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.CategoriasDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.listadodetallelibreria.adaptadores.ListaAdaptador;

import java.util.List;

/**
 * Created by h on 1/11/15.
 */
public class NotificacionAdaptador extends ListaAdaptador<NotificacionDTO> {

    public NotificacionAdaptador(Activity actividad, List<NotificacionDTO> listaNotifiacciones) {
        super(actividad, listaNotifiacciones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategoriasDataSource catDataSource = new CategoriasDataSource(actividad);
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.notificacion_view_adapter, null, true);

        try {
            catDataSource.open();

            List<NotificacionDTO> lista = (List<NotificacionDTO>) listaObjetos;
            NotificacionDTO objeto = lista.get(position);

            view.setTag(objeto);
            TextView textNombreSitio = (TextView) view.findViewById(R.id.textTituloNotificacion);
            textNombreSitio.setText(objeto.getTitulo());

            CategoriaDTO categoria = catDataSource.getById(objeto.getIdCategoria());

            ImageView imagen = (ImageView) view.findViewById(R.id.imagenListaNotificaciones);
            ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(actividad);
            Bitmap bitmap = almacenamiento.getIconoCategoria(categoria.getId(), categoria.getNombre());
            imagen.setImageBitmap(bitmap);
        } finally {
            catDataSource.close();
        }

        return view;
    }
}
