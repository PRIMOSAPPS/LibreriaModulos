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
    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView textView;
        private NotificacionDTO notificacion;

        public ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }

        public NotificacionDTO getNotificacion() {
            return notificacion;
        }

        public void setNotificacion(NotificacionDTO notificacion) {
            this.notificacion = notificacion;
        }
    }

    public NotificacionAdaptador(Activity actividad, List<NotificacionDTO> listaNotifiacciones) {
        super(actividad, listaNotifiacciones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoriasDataSource catDataSource = new CategoriasDataSource(actividad);


        ImageView imagen;
        TextView textNombreSitio;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = actividad.getLayoutInflater();
            convertView = inflater.inflate(R.layout.notificacion_view_adapter, null, true);

            imagen = (ImageView) convertView.findViewById(R.id.imagenListaNotificaciones);
            textNombreSitio = (TextView) convertView.findViewById(R.id.textTituloNotificacion);
            viewHolder = new ViewHolder(imagen, textNombreSitio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imagen = viewHolder.imageView;
            textNombreSitio = viewHolder.textView;
        }



        //LayoutInflater inflater = actividad.getLayoutInflater();
        //View view = inflater.inflate(R.layout.notificacion_view_adapter, null, true);

        try {
            catDataSource.open();

            List<NotificacionDTO> lista = (List<NotificacionDTO>) listaObjetos;
            NotificacionDTO objeto = lista.get(position);

            viewHolder.setNotificacion(objeto);
            //TextView textNombreSitio = (TextView) view.findViewById(R.id.textTituloNotificacion);
            textNombreSitio.setText(objeto.getTitulo());

            CategoriaDTO categoria = catDataSource.getById(objeto.getIdCategoria());

            //ImageView imagen = (ImageView) view.findViewById(R.id.imagenListaNotificaciones);
            ItfAlmacenamiento almacenamiento = AlmacenamientoFactory.getAlmacenamiento(actividad);
            Bitmap bitmap = almacenamiento.getIconoCategoria(categoria.getId(), categoria.getNombre());
            imagen.setImageBitmap(bitmap);
        } finally {
            catDataSource.close();
        }

        return convertView;
    }
}
