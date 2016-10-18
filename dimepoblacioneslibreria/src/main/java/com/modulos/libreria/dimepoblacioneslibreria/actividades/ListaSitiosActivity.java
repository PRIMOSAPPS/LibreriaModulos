package com.modulos.libreria.dimepoblacioneslibreria.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle.DetalleSitioActivity;
import com.modulos.libreria.dimepoblacioneslibreria.adaptadores.SitioAdaptador;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.SitiosDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.utilidadeslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.utilidadeslibreria.permisos.Permisos;

import java.util.List;

public class ListaSitiosActivity extends AppCompatActivity {

    private SitiosDataSource dataSource;
    private String categoriaSeleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_evento_lista_normal);
        setContentView(R.layout.activity_lista_sitios);

        dataSource = new SitiosDataSource(this);
        dataSource.open();

        categoriaSeleccionada = (String) getIntent().getExtras().get("categoria");
//        String favoritos = (String) getIntent().getExtras().get("favoritos");

//        String strParaTitulo = categoriaSeleccionada;
        // Se asigna el titulo del action bar
//        setTitulo(strParaTitulo);

        Permisos permisosUtil = new Permisos();
        if(!permisosUtil.preguntarPermisos(this, ItfAlmacenamiento.permisosNecesarios)) {
            cargarSitios();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Permisos.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                cargarSitios();
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void mostrarDetalle(View view) {
        SitioAdaptador.ViewHolder viewHolder = (SitioAdaptador.ViewHolder)view.getTag();
        SitioDTO sitio = viewHolder.getSitio();

        Intent intent = new Intent(this, DetalleSitioActivity.class);
        intent.putExtra(DetalleSitioActivity.ID_SITIO, sitio.getId());
        startActivity(intent);
    }

    private void cargarSitios() {
        List<SitioDTO> listaSitios = null;
        listaSitios = dataSource.getByCategoria(categoriaSeleccionada);

        SitioAdaptador adapter = new SitioAdaptador(this, listaSitios);
        ListView mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(adapter);
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

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lista_sitios);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_lista_sitios, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}