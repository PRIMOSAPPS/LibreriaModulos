package com.modulos.libreria.buzonciudadanolibreria;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.modulos.libreria.buzonciudadanolibreria.adaptador.GalleryPagerAdapter;
import com.modulos.libreria.buzonciudadanolibreria.mail.AsyncTaskMailSender;
import com.modulos.libreria.buzonciudadanolibreria.mail.GMailSender;
import com.modulos.libreria.utilidadeslibreria.gps.Gps;
import com.modulos.libreria.utilidadeslibreria.util.GoogleMaps;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BuzonCiudadanoActivity extends AppCompatActivity implements Gps.GpsListener, GoogleMaps.GoogleMapsGeocodeListener {
    private final static String TAG = "BuzonCiudadanoActivity";
    public final static String DIRECTORIO = "DIRECTORIO_BUZON_CIUDADANO";
    public final static String NUM_FOTOS = "NUM_FOTOS_BUZON_CIUDADANO";
    private final static int FOTO_PARA_BUZON_CIUDADANO = 1;
    private Uri fotoUri;
    private int contadorFotos = 0;
    private int indiceFotoSacada = -1;
    private Gps gps;
    private int numFotosPermitidas = 3;
    private List<Uri> lstUrisFotos;
    private String directorioFotos = Environment.DIRECTORY_PICTURES;

    // mainLayout is the child of the HorizontalScrollView ...
    private LinearLayout mainLayout;
    private View cell;
    private TextView text;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon_ciudadano);

        gps = new Gps(this);
        gps.registrarLocationManager(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            directorioFotos = (String) extras.get(DIRECTORIO);

            Integer numeroFotos = (Integer) getIntent().getExtras().get(NUM_FOTOS);
            if (numeroFotos != null) {
                numFotosPermitidas = numeroFotos;
            }
        }

        lstUrisFotos = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.libCiuViewPager);
        mainLayout = (LinearLayout) findViewById(R.id.libCiudLinearGallery);

//        pruebaGaleria();
    }

//    private void pruebaGaleria() {
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                directorioFotos), "FotoBuzonCiudadano_0.jpg");
//        fotoUri = Uri.fromFile(file);
//        addFotoGaleria();
//        file = new File(Environment.getExternalStoragePublicDirectory(
//                directorioFotos), "FotoBuzonCiudadano_1.jpg");
//        fotoUri = Uri.fromFile(file);
//        addFotoGaleria();
//        file = new File(Environment.getExternalStoragePublicDirectory(
//                directorioFotos), "FotoBuzonCiudadano_2.jpg");
//        fotoUri = Uri.fromFile(file);
//        addFotoGaleria();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buzon_ciudadano, menu);
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

    /**
     * Devuelve el primer indice de foto libre.
     *
     * @return
     */
//    private int getIndiceFotoLibre() {
//        int resul = -1;
//        for (int i = 0; i < arrUrisFotos.length; i++) {
//            if (arrUrisFotos[i] == null) {
//                resul = i;
//                break;
//            }
//        }
//        Log.d(TAG, "El indice de foto libre es " + resul);
//        return resul;
//    }

    private boolean isSacarFotoPermitido() {
        return lstUrisFotos.size() < numFotosPermitidas;
    }

    private String getNombreFoto() {
        String resul = "FotoBuzonCiudadano_{0}.jpg";

        return MessageFormat.format(resul,contadorFotos++);
    }

    public void sacarFoto(View view) {
        if (!isSacarFotoPermitido()) {
            Toast.makeText(this, "Numero maximo de fotos alcanzado.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                directorioFotos), getNombreFoto());
        //File file = new File(getCacheDir().getAbsolutePath(), getNombreFoto());
        fotoUri = Uri.fromFile(file);

        lstUrisFotos.add(fotoUri);

        Toast.makeText(this, "El directorio temporal es: " + getCacheDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Imagen guardada en: " + fotoUri.toString(), Toast.LENGTH_LONG).show();

        i.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
        startActivityForResult(i, FOTO_PARA_BUZON_CIUDADANO);
    }

    private void addFotoGaleria() {
        cell = getLayoutInflater().inflate(R.layout.celda_galeria, null);
        final ImageView imageView = (ImageView) cell.findViewById(R.id.libCiuImagen);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                viewPager.setVisibility(View.VISIBLE);
                viewPager.setAdapter
                        (new GalleryPagerAdapter(BuzonCiudadanoActivity.this, lstUrisFotos));
                viewPager.setCurrentItem(v.getId());
            }
        });

        final ImageView imageViewBorrar = (ImageView) cell.findViewById(R.id.libCiuBorrarImagen);
        Pair<View, Uri> celdaUri = new Pair<>(cell, fotoUri);
        imageViewBorrar.setTag(celdaUri);

        text = (TextView) cell.findViewById(R.id.libCiudNombreImagen);
        text.setText("Image#" + (fotoUri.toString()));

        imageView.setFitsSystemWindows(true);
        imageView.setImageURI(fotoUri);

        mainLayout.addView(cell);
    }

    public void borrarImagen(View view) {
        Pair<View, Uri> celdaUri = (Pair) view.getTag();
        View celda = celdaUri.first;
        Uri fotoUri = celdaUri.second;
        mainLayout.removeView(celda);
        lstUrisFotos.remove(fotoUri);
        borrarFoto(fotoUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FOTO_PARA_BUZON_CIUDADANO) {
                Toast.makeText(this, "Imagen guardada en: " + fotoUri.toString(), Toast.LENGTH_LONG).show();
                addFotoGaleria();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Accion cancelada por el usuario.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al capturar la foto.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.pausarRegistro();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps.registrarLocationManager();
    }

    private void borrarFoto(Uri strFoto) {
        if(true)return;
        if (strFoto != null) {
            File fich = new File(strFoto.getPath());
            fich.delete();
        }
    }

    private void borrarFotos() {
        for (Uri uriFoto : lstUrisFotos) {
            borrarFoto(uriFoto);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        //borrarFotos();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        borrarFotos();
    }

    @Override
    public void actualizacionPosicion(Location locationActual) {
        TextView tv = (TextView) findViewById(R.id.libCiuTextDireccion);
        if (tv != null) {
            tv.setText("Nueva localizacion: " + locationActual.getLatitude() + ", " + locationActual.getLongitude());
        }
        GoogleMaps gm = new GoogleMaps();
        gm.conseguirDireccion(locationActual, this);
    }

    @Override
    public void direccionFromLocation(String direccion) {
        TextView tv = (TextView) findViewById(R.id.libCiuTextDireccion);
        if (tv != null) {
            tv.setText(direccion);
        }
    }

    public void comentar(View view) {
//        Preference acercaDe = (Preference) findPreference(PREFERENCIA_ACERCA_DE);
//        acercaDe.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
        Dialog myDialog = new Dialog(BuzonCiudadanoActivity.this);
        myDialog.setContentView(R.layout.comentario_lib_ciu);
        myDialog.setTitle(R.string.comentario);
        myDialog.show();
        TextView textComentario = (TextView) myDialog.findViewById(R.id.libCiuTextComentario);
//            }
//        });
    }

    public void enviar(View view) {
        Log.d(TAG, "Se realiza el envio del correo.");
        GMailSender sender = new GMailSender("primos.apps@gmail.com", "cacharreando2014");
        AsyncTaskMailSender taskMAilSender = new AsyncTaskMailSender(sender);
        taskMAilSender.execute((Void) null);

//        try {
//            sender.sendMail("This is Subject",
//                    "This is Body",
//                    "user@gmail.com",
//                    "user@yahoo.com");
//        } catch (MessagingException e) {
//            Log.e(TAG, "Error al enviar el correo", e);
//        }
    }


    @Override
    public void onBackPressed() {
        if (viewPager != null && viewPager.isShown()) {
            viewPager.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
