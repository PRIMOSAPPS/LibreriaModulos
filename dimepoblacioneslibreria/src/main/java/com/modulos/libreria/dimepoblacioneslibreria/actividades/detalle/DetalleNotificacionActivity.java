package com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.NotificacionesDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.utilidadeslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.utilidadeslibreria.constantes.Constantes;
import com.modulos.libreria.utilidadeslibreria.permisos.Permisos;

public class DetalleNotificacionActivity extends AppCompatActivity {
    public final static String ID_NOTIFICACION = "ID_NOTIFICACION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_notificacion);

        Permisos permisosUtil = new Permisos();
        permisosUtil.preguntarPermisos(this, ItfAlmacenamiento.permisosNecesarios);

        TextView titulo = (TextView)findViewById(R.id.dimeNotifTitulo);
        WebView texto = (WebView)findViewById(R.id.dimeNotifTexto);

        long idNotificacion = (long) getIntent().getExtras().get(ID_NOTIFICACION);

        NotificacionesDataSource dataSource = new NotificacionesDataSource(this);
        dataSource.open();
        NotificacionDTO notificacion = dataSource.getById(idNotificacion);
        dataSource.close();

        titulo.setText(notificacion.getTitulo());
        texto.loadDataWithBaseURL(null, notificacion.getTexto(), Constantes.mimeType, Constantes.encoding, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_notificacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
