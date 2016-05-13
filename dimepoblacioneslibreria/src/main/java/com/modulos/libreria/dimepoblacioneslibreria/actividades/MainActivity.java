package com.modulos.libreria.dimepoblacioneslibreria.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageSwitcher;

import com.modulos.libreria.buzonciudadanolibreria.BuzonCiudadanoActivity;
import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.constantes.Constantes;
import com.modulos.libreria.dimepoblacioneslibreria.util.Propiedades;
import com.modulos.libreria.radiolibreria.StreamPlayerActivity;
import com.modulos.libreria.utilidadeslibreria.menulateral.ControlMenuLateral;
import com.modulos.libreria.utilidadeslibreria.util.GoogleMaps;
import com.modulos.libreria.utilidadeslibreria.util.UtilPropiedades;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    //private DrawerLayout drawerLayout;
    //private LinearLayout linearLayoutMenuLateral;
    private ControlMenuLateral controlMenuLateral;

    private ImageSwitcher imageSwitcher;

    private int[] gallery = { };

    private int position;

    private static final Integer DURATION = 2500;

    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "iniciar()");

        //linearLayoutMenuLateral = (LinearLayout) findViewById(R.id.linearLayoutMenuLateral);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //controlMenuLateral = new ControlMenuLateral(this, R.id.linearLayoutMenuLateral, R.id.drawer_layout);

        //configMenuLateral();

        //imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSlider);
        //imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

        //    public View makeView() {
        //        return new ImageView(MainActivity.this);
        //    }
        //});

        // Action bar
        //ActionBar actionBar = getSupportActionBar();
        //View customActionBarView = getLayoutInflater().inflate(R.layout.action_bar_custom, null);
        //actionBar.setCustomView(customActionBarView);
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Set animations
        // https://danielme.com/2013/08/18/diseno-android-transiciones-entre-activities/
        //Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        //Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        //imageSwitcher.setInAnimation(fadeIn);
        //imageSwitcher.setOutAnimation(fadeOut);

        //startSlider();

    }

    /*
    protected void configMenuLateral() {
        ConfigMenuLateral cml = new ConfigMenuLateral();
        cml.configurarMenuLateral(this, R.id.listMenuLateral, R.id.drawer_layout);
        ConfigMenuLateralFactory.getInstance().setConfigMenuLateral(cml);
    }
    */

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
            mostrarOcultarMenuLateral();
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
        String strLatitud = utilProp.getProperty(Propiedades.PROP_LATITUD_PUEBLO);
        String strLongtitud = utilProp.getProperty(Propiedades.PROP_LONGITUD_PUEBLO);
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

    private void mostrarOcultarMenuLateral() {
        controlMenuLateral.mostrarOcultarMenuLateral();
        /*
        if (drawerLayout.isDrawerOpen(linearLayoutMenuLateral)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(linearLayoutMenuLateral);
        }
        */
    }

    public void mostrarMenuLateral(View view) {
        mostrarOcultarMenuLateral();
    }

    public void iniciarRadio(View view) {
        Intent i = new Intent(this, StreamPlayerActivity.class);
        String urlRadio = UtilPropiedades.getInstance().getProperty(Propiedades.PROP_URL_RADIO);
        i.putExtra(StreamPlayerActivity.URL_RADIO, urlRadio);
        startActivity(i);
    }

    public void irBuzonCiudadano(View view) {
        Intent i = new Intent(this, BuzonCiudadanoActivity.class);
        //i.putExtra(BuzonCiudadanoActivity.DIRECTORIO, Environment.DIRECTORY_PICTURES);
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

    /*
    protected void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);
    }
    */

    /*
    // Stops the slider when the Activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    */


}
