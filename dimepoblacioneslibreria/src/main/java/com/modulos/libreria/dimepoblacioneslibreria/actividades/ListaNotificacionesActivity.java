package com.modulos.libreria.dimepoblacioneslibreria.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleNotificacionActivity;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleSitioActivity;
import com.modulos.libreria.dimepoblacioneslibreria.adaptadores.NotificacionAdaptador;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.NotificacionesDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.NotificacionDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;

import java.util.List;

public class ListaNotificacionesActivity extends AppCompatActivity {
    public final static String ID_CATEGORIA = "idCategoria";

    private NotificacionesDataSource dataSource;

    private Long idCategoriaSeleccionada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notificaciones);

        dataSource = new NotificacionesDataSource(this);
        dataSource.open();

        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null) {
            idCategoriaSeleccionada = new Long(intent.getExtras().getLong(ID_CATEGORIA));
        }

        cargarNotificaciones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_notificaciones, menu);
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

    private void cargarNotificaciones() {
        List<NotificacionDTO> listaNotificaciones = null;
        if(idCategoriaSeleccionada == null) {
            listaNotificaciones = dataSource.getAll();
        } else {
            listaNotificaciones = dataSource.getByCategoria(idCategoriaSeleccionada);
        }

        NotificacionAdaptador adapter = new NotificacionAdaptador(this, listaNotificaciones);
        ListView mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(adapter);
    }


    public void mostrarDetalle(View view) {
        NotificacionDTO notificacion = (NotificacionDTO)view.getTag();

        Intent intent = new Intent(this, DetalleNotificacionActivity.class);
        intent.putExtra(DetalleNotificacionActivity.ID_NOTIFICACION, notificacion.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
