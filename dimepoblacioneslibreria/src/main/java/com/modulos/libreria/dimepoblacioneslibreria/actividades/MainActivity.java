package com.modulos.libreria.dimepoblacioneslibreria.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.modulos.libreria.buzonciudadanolibreria.BuzonCiudadanoActivity;
import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.constantes.Constantes;
import com.modulos.libreria.dimepoblacioneslibreria.menulateral.ConfigMenuLateral;
import com.modulos.libreria.dimepoblacioneslibreria.util.UtilPropiedades;
import com.modulos.libreria.radiolibreria.StreamPlayerActivity;
import com.modulos.libreria.utilidadeslibreria.util.GoogleMaps;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] opciones = {"Radio", "Puntos de interes", "Colaboracion ciudadana", "Bandos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "iniciar()");

        listView = (ListView) findViewById(R.id.listMenuLateral);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ConfigMenuLateral cml = new ConfigMenuLateral(this);
        cml.iniciarMenuLateral();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            irPreferencias();
            return true;
        } else if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(listView)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(listView);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void irPreferencias() {
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }

//
//    public void verMisMapa(View view) {
//        Intent i = new Intent(this, MapsActivity.class);
//        startActivity(i);
//    }

    public void verGoogleMapa(View view) {
//        String paqueteMapa = "com.google.android.apps.maps";
//        Uri gmmIntentURi = Uri.parse("geo:38.0891004,-6.2775499?z=13&q=restaurants");
//
//        Intent i = new Intent(Intent.ACTION_VIEW, gmmIntentURi);
//        i.setPackage(paqueteMapa);
        GoogleMaps gm = new GoogleMaps();
        UtilPropiedades utilProp = UtilPropiedades.getInstance();
        String strLatitud = utilProp.getProperty(UtilPropiedades.PROP_LATITUD_PUEBLO);
        String strLongtitud = utilProp.getProperty(UtilPropiedades.PROP_LONGITUD_PUEBLO);
        double doubleLatitud = Double.parseDouble(strLatitud);
        double doubleLongitud = Double.parseDouble(strLongtitud);
        startActivity(gm.getUrlMapsApps(doubleLatitud, doubleLongitud, "Monesterio", 13));
    }

//    public void verMiGoogleMapa(View view) {
//        String paqueteMapa = "com.google.android.apps.maps";
//        //Uri gmmIntentURi = Uri.parse("https://www.google.com/maps/d/edit?mid=zrLnu9NSuviA.kp0vd2lJTAJg&usp=sharing");
//        Uri gmmIntentURi = Uri.parse("geo:38.0891004,-6.2775499?z=13&mid=zrLnu9NSuviA.kp0vd2lJTAJg");
//
//        Intent i = new Intent(Intent.ACTION_VIEW, gmmIntentURi);
//        i.setPackage(paqueteMapa);
//        startActivity(i);
//    }

    public void iniciarRadio(View view) {
        Intent i = new Intent(this, StreamPlayerActivity.class);
        String urlRadio = UtilPropiedades.getInstance().getProperty(UtilPropiedades.PROP_URL_RADIO);
        i.putExtra(StreamPlayerActivity.URL_RADIO, urlRadio);
        startActivity(i);
    }

    public void irBuzonCiudadano(View view) {
        Intent i = new Intent(this, BuzonCiudadanoActivity.class);
        i.putExtra(BuzonCiudadanoActivity.DIRECTORIO, Environment.DIRECTORY_PICTURES);
        startActivity(i);
    }

    public void irNotificaciones(View view) {
        Intent i = new Intent(this, ListaNotificacionesActivity.class);
//        i.putExtra(ListaNotificacionesActivity.ID_CATEGORIA, );
        startActivity(i);
    }

    public void irSitios(View view) {
        Intent i = new Intent(this, ListaSitiosActivity.class);
        i.putExtra(Constantes.categoria, Constantes.SITIO);
        startActivity(i);
    }


}
