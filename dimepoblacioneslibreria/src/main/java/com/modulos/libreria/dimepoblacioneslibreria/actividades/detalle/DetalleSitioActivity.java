package com.modulos.libreria.dimepoblacioneslibreria.actividades.detalle;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.adaptadores.ImageAdapter;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.CategoriasDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.SitiosDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.utilidadeslibreria.constantes.Constantes;
import com.modulos.libreria.utilidadeslibreria.util.GoogleMaps;

public class DetalleSitioActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory,
        View.OnClickListener {

    public final static String ID_SITIO = "idSitio";
    public final static String SITIO = "sitio";
    private final static String TAG = "[DetalleEventoActivity]";
    private SitiosDataSource dataSource = null;
    private SitioDTO sitio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new SitiosDataSource(this);
        dataSource.open();
        setContentView(R.layout.activity_detalle_sitio);

        TextView textViewDireccion = (TextView) findViewById(R.id.textDireccion);
        final WebView webViewTextoLargo1 = (WebView) findViewById(R.id.wvSitioTextoLargo1);
        TextView textNombreSitio = (TextView) findViewById(R.id.textNombreSitio);
        TextView textNombreSitio2 = (TextView) findViewById(R.id.textNombreSitio2);
        TextView textTelefono = (TextView) findViewById(R.id.textTelefono);


        ImageButton botonTelefono = (ImageButton) findViewById(R.id.botonTelefono);
        ImageButton botonLocalizar = (ImageButton) findViewById(R.id.botonLocalizar);
        ImageButton botonCompartir = (ImageButton) findViewById(R.id.botonCompartir);
        ImageButton botonFacebook = (ImageButton) findViewById(R.id.botonFacebook);
        ImageButton botonTwiter = (ImageButton) findViewById(R.id.botonTwiter);
        ImageButton botonWeb = (ImageButton) findViewById(R.id.botonWeb);

        ImageButton botonCorreo = (ImageButton) findViewById(R.id.botonCorreo);


        //miBotonB.setVisibility(View.GONE);
        //ocultarBoton(sitio);



        //botonTelefono.setOnClickListener(this);
        //botonLocalizar.setOnClickListener(this);
        botonCompartir.setOnClickListener(this);
        //botonFacebook.setOnClickListener(this);
        //botonTwiter.setOnClickListener(this);
        //botonWeb.setOnClickListener(this);

        long idSitio = (long) getIntent().getExtras().get(ID_SITIO);
        this.sitio = dataSource.getById(idSitio);

        Gallery myGallery = (Gallery) findViewById(R.id.gallery);
        myGallery.setAdapter(new ImageAdapter(this, this, sitio));

        //textViewDireccion.setText(getTxtDatosSitio(sitio));
        textViewDireccion.setText(sitio.getDireccion()+" - "+sitio.getPoblacion());
        textNombreSitio.setText(sitio.getNombre());
        textNombreSitio2.setText(sitio.getNombre());

        if (sitio.getTelefonosFijos().length()>0) {
            if (sitio.getTelefonosMoviles().length()>0) {
                textTelefono.setText(sitio.getTelefonosFijos()+" / "+sitio.getTelefonosMoviles());
                botonTelefono.setOnClickListener(this);
            }else {
                textTelefono.setText(sitio.getTelefonosFijos());
            }
        }else {
            if (sitio.getTelefonosMoviles().length()>0) {
                textTelefono.setText(sitio.getTelefonosMoviles());
                botonTelefono.setOnClickListener(this);
            }else {
                textTelefono.setText(sitio.getTelefonosFijos());
                botonTelefono.setVisibility(View.GONE);
                botonTelefono.setOnClickListener(this);

            }
        }

        //Oculta botón Localizar si no tiene Coordenadas.
        mostrarOcultarIcono(botonLocalizar, sitio.getLatitud());
        //Oculta botón Compartir si no tiene Coordenadas.
        mostrarOcultarIcono(botonCompartir, sitio.getLatitud());
        //Oculta botón enviarCorreo si no tiene correo.
        mostrarOcultarIcono(botonCorreo, sitio.getEmail());
        //Oculta botón web si no tiene web.
        mostrarOcultarIcono(botonWeb, sitio.getWeb());
        //Oculta botón Facebook si no tiene Facebook.
        mostrarOcultarIcono(botonFacebook, sitio.getFacebook());
        //Oculta botón Twiter si no tiene Twiter.
        mostrarOcultarIcono(botonTwiter, sitio.getTwitter());

//		textViewTextoLargo1.setText(sitio.getTextoLargo1());
        String txtTextoLargo1 = sitio.getTextoLargo1();
        String txtTextoLargo2 = sitio.getTextoLargo2();
        String textoWebView = txtTextoLargo1 + "<div style='clear:both;'></div>" + txtTextoLargo2;
        webViewTextoLargo1.loadDataWithBaseURL(null, textoWebView, Constantes.mimeType, Constantes.encoding, null);

        // Se asigna el titulo del action bar
        CategoriasDataSource categoriaDataSource = new CategoriasDataSource(this);
        categoriaDataSource.open();
        CategoriaDTO categoria = categoriaDataSource.getById(sitio.getIdCategoria());
//        setTitulo(categoria.getNombre());
        categoriaDataSource.close();

    }

    private void mostrarOcultarIcono(ImageButton boton, String texto) {
        mostrarOcultarIcono(boton, texto.trim().length()>0);
    }

    private void mostrarOcultarIcono(ImageButton boton, double valor) {
        mostrarOcultarIcono(boton, valor>0);
    }

    private void mostrarOcultarIcono(ImageButton boton, boolean mostrar) {
        if (mostrar) {
            boton.setOnClickListener(this);
        }else {
            boton.setVisibility(View.GONE);
        }
    }

    // Recogemos la pulsación en los 5 botones de la minificha
    public void onClick(View boton_pulsado) {

        String lugar = sitio.getNombre();

        Double latitud = sitio.getLatitud();
        Double longitud = sitio.getLongitud();

        int i = boton_pulsado.getId();
        if (i == R.id.botonTelefono) {
            realizarLlamada(sitio.getTelefonosFijos());

        } else if (i == R.id.botonLocalizar) {
            localizarSitio(lugar, latitud, longitud);

        } else if (i == R.id.botonCompartir) {
            compartirLugar(lugar, sitio.getLatitud(), sitio.getLongitud());

        } else if (i == R.id.botonCorreo) {
            enviarCorreo(sitio.getEmail());

        } else if (i == R.id.botonFacebook) {
            mostrarFacebook(sitio.getFacebook());

        } else if (i == R.id.botonTwiter) {
            mostrarTwiter(sitio.getTwitter());

        } else if (i == R.id.botonWeb) {
            visitarWeb(sitio.getWeb());

        } else {
        }

    }

    // Abre Internet para ir al sitio WEB del anunciante, si no lo tiene
    // aparece un mensajito.
    // NO SE SI SERIA MEJOR LO DEL MENSAJITO O QUE NO APARECIERA EL ICONO DE
    // TWITER, pero pienso que hay que
    // motivar a las empresas para que esten en las redes sociales.
    private void visitarWeb(String url) {
        try {

            if (url.length() > 0) {


                Uri irWeb = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, irWeb);
                startActivity(intent);
            } else {
                // si el sitio no tiene WEB
                Toast.makeText(getBaseContext(), R.string.lib_dime_no_disponible,
                        Toast.LENGTH_SHORT).show();
            }

        } catch (ActivityNotFoundException activityException) {
            // si se produce un error, se muestra en el LOGCAT
            Toast.makeText(getBaseContext(), R.string.lib_dime_no_acceso_web,
                    Toast.LENGTH_SHORT).show();

        }
    }


    // Abre Internet para ir al sitio Twiter del anunciante, si no lo tiene
    // aparece un mensajito.
    // NO SE SI SERIA MEJOR LO DEL MENSAJITO O QUE NO APARECIERA EL ICONO DE
    // TWITER, pero pienso que hay que
    // motivar a las empresas para que esten en las redes sociales.
    private void mostrarTwiter(String twitter) {
        try {
            if (twitter.length() > 0) {
                Uri irTwiter = Uri.parse(twitter);
                Intent intent = new Intent(Intent.ACTION_VIEW, irTwiter);
                startActivity(intent);
            } else {
                // si el sitio no tiene Twiter
                Toast.makeText(getBaseContext(), R.string.lib_dime_twiter_no_disponible,
                        Toast.LENGTH_SHORT).show();
            }

        } catch (ActivityNotFoundException activityException) {
            // si se produce un error, se muestra en el LOGCAT
            Toast.makeText(getBaseContext(), R.string.lib_dime_no_acceso_twiter,
                    Toast.LENGTH_SHORT).show();
            // Log.e("ET", "No se pudo realizar la llamada.",
            // activityException);
        }
    }

    // Muy pronto estará terminado
    private void localizarSitio(String lugar, Double latitud, Double longitud) {
//        Intent lanzarmapa = new Intent(this, MapaLugaresActivity.class);
//
//        // Vamos a pasar los valores que necesita MapaLugarActivity
//        lanzarmapa.putExtra("nombre", sitio.getNombre());
//        lanzarmapa.putExtra("latitud", latitud);
//        lanzarmapa.putExtra("longitud", longitud);
//        lanzarmapa.putExtra(MapaLugaresActivity.ORIGEN, SITIO);
//        lanzarmapa.putExtra(MapaLugaresActivity.ID_RECIBIDO, sitio.getId());
//
//
//        startActivity(lanzarmapa);
//        finish();

        GoogleMaps gm = new GoogleMaps();
        startActivity(gm.getUrlMapsApps(latitud, longitud, 8));
    }

    // Abre Internet para ir al sitio Facebook del anunciante, si no lo tiene
    // aparece un mensajito.
    // NO SE SI SERIA MEJOR LO DEL MENSAJITO O QUE NO APARECIERA EL ICONO DE
    // FACEBOOK, pero pienso que hay que
    // motivar a las empresas para que esten en las redes sociales.
    private void mostrarFacebook(String facebook) {
        try {
            if (facebook.length() > 0) {
                // realiza la llamada
                Uri irFacebook = Uri.parse(facebook);
                Intent intent = new Intent(Intent.ACTION_VIEW, irFacebook);
                startActivity(intent);
            } else {
                // si el sitio no tiene facebook
                Toast.makeText(getBaseContext(), R.string.lib_dime_facebook_no_disponible,
                        Toast.LENGTH_SHORT).show();
            }

        } catch (ActivityNotFoundException activityException) {
            // si se produce un error, se muestra en el LOGCAT
            Toast.makeText(getBaseContext(), R.string.lib_dime_no_acceso_facebook,
                    Toast.LENGTH_SHORT).show();
            // Log.e("ET", "No se pudo realizar la llamada.",
            // activityException);
        }

    }

    // Seleccionar de una lista de aplicaciones instaladas en el movil, una para
    // enviar una notificación a quien quiera
    // para indicar donde se encuentra ese lugar en concreto, sigue con el
    // problema de los decimales en lat y long.
    private void compartirLugar(String lugar, Double lat, Double lon) {
        String frase1, frase2;

        String latitud = Double.toString(lat);
        String longitud = Double.toString(lon);

        frase1 = getString(R.string.compartir1);
        frase2 = getString(R.string.compartir2);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String titulo = frase1 + " " + lugar + frase2;
        String url = "http://maps.google.com/maps?q=" + lat + "," + lon;
        // Toast.makeText(getBaseContext(),"2-"+latitud+"/"+longitud+"--"+lat+"///"+lon,Toast.LENGTH_SHORT).show();
        intent.putExtra(Intent.EXTRA_SUBJECT, titulo);// Encabezado del mensaje
        intent.putExtra(Intent.EXTRA_TEXT, url);// Direccion web
        startActivity(Intent.createChooser(intent,
                this.getString(R.string.titulo_compartir)));

    }

    // Seleccionar de una lista de aplicaciones instaladas en el movil, una para
    // enviar un correo al sitio
    //private void enviarCorreo(String lugar, Double lat, Double lon) {
    private void enviarCorreo(String correo) {


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String[] destinatarios = {correo};
        intent.putExtra(Intent.EXTRA_EMAIL, destinatarios);
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.lib_dime_asunto_correo);// Asunto del mensaje
        //intent.putExtra(Intent.EXTRA_TEXT, "cuerpo del mensaje");// Cuerpo del Mensaje
        //String strTexto = Resources.getSystem().getString(R.string.lib_dime_selec_app_correo);
        String strTexto = "Seleccionar aplicación de correo";
        startActivity(Intent.createChooser(intent, strTexto));
        //      this.getString(R.string.titulo_compartir)));

    }



    // Lanza el DIAL para que solo sonlo con pulsar un boton se realice una
    // llamada, lo hice con llamda directa, pero
    // aparte que había que modificar permisos en el manifest qu eno me gustan
    // ni a la gente tampoco, me parecía
    // demasiado atrevido.
    private void realizarLlamada(String numero) {
        // TODO Auto-generated method stub
        numero = numero.trim();
        // Toast.makeText(getBaseContext(),numero,Toast.LENGTH_SHORT).show();
        try {
            if (numero.length() > 0) {
                // realiza la llamada
                Uri marcarnumero = Uri.parse("tel:" + numero.toString());
                Intent intent = new Intent(Intent.ACTION_DIAL, marcarnumero);
                startActivity(intent);
            } else {
                // si el sitio no tiene numero
                Toast.makeText(getBaseContext(),
                        "Sin Numero de Telefono Asociado", Toast.LENGTH_SHORT)
                        .show();
            }

        } catch (ActivityNotFoundException activityException) {
            // si se produce un error, se muestra en el LOGCAT
            Toast.makeText(getBaseContext(), R.string.lib_dime_no_pudo_llamar,
                    Toast.LENGTH_SHORT).show();
            // Log.e("ET", "No se pudo realizar la llamada.",
            // activityException);
        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.actionbar_inicio:
//                Intent i = new Intent(this, MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.putExtra(MainActivity.ACTUALIZAR, false);
//                startActivity(i);
//                return true;
//            case R.id.actionbar_favorito:
//                cambiarEstadoFavorito(item);
//                return true;
//            case R.id.actionbar_eventos:
//                Intent iEventos = new Intent(this, ListaEventosActivity.class);
//                iEventos.putExtra(ListaEventosActivity.ID_SITIO, sitio.getId());
//                startActivity(iEventos);
//                return true;
//        }
//        return false;
//    }

    /**
     * Cambia el estado de favorito del sitio, actualiza el icono y lo guarda en
     * la base de datos
     *
     * @param item
     */
    private void cambiarEstadoFavorito(MenuItem item) {
        sitio.setFavorito(!sitio.isFavorito());
//        asignarIconoFavorito(item);
        dataSource.actualizar(sitio);
    }

//    /**
//     * Asigna el icono correspondiente al estado de favorito.
//     *
//     * @param item
//     */
//    private void asignarIconoFavorito(MenuItem item) {
//        int iconFavorito = R.drawable.ic_action_nofavorito;
//        if (sitio.isFavorito()) {
//            iconFavorito = R.drawable.ic_action_favorito;
//        }
//        item.setIcon(iconFavorito);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detalle_evento, menu);
//        asignarIconoFavorito(menu.findItem(R.id.actionbar_favorito));
//        lstEventosSitio = eventosDataSource.getBySitioId(sitio.getId());
//        Log.d(TAG, "Numero de eventos para el sitio " + sitio.getNombre() + " es " + lstEventosSitio.size());
//        if(lstEventosSitio.isEmpty()) {
//            MenuItem item = menu.findItem(R.id.actionbar_eventos);
//            item.setVisible(false);
//        }
//        return true;
//    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return i;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        Log.d(TAG, "Mostrada imagen");
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // mySelection.setText("Nothing selected");
    }

    @Override
    protected void onResume() {
        dataSource.open();
//        eventosDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
//        eventosDataSource.close();
        super.onPause();
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detalle_sitio);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detalle_sitio, menu);
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
