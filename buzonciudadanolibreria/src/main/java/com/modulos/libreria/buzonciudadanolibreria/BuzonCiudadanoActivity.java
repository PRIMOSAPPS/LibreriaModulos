package com.modulos.libreria.buzonciudadanolibreria;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.modulos.libreria.buzonciudadanolibreria.adaptador.GalleryPagerAdapter;
import com.modulos.libreria.buzonciudadanolibreria.mail.AsyncTaskMailSender;
import com.modulos.libreria.buzonciudadanolibreria.mail.MailSender;
import com.modulos.libreria.utilidadeslibreria.almacenamiento.AlmacenamientoUtilFactory;
import com.modulos.libreria.utilidadeslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.utilidadeslibreria.gps.Gps;
import com.modulos.libreria.utilidadeslibreria.util.GoogleMaps;
import com.modulos.libreria.utilidadeslibreria.util.UtilImagenes;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuzonCiudadanoActivity extends AppCompatActivity implements Gps.GpsListener, GoogleMaps.GoogleMapsGeocodeListener, AsyncTaskMailSender.MailSenderListener {
    private final static String TAG = "BuzonCiudadanoActivity";
    public final static String DIRECTORIO = "DIRECTORIO_BUZON_CIUDADANO";
    public final static String NUM_FOTOS = "NUM_FOTOS_BUZON_CIUDADANO";
    private final static int FOTO_PARA_BUZON_CIUDADANO = 1;
    private Uri fotoUri;
    private int contadorFotos = 0;
    private Gps gps;
    private int numFotosPermitidas = 3;
    private List<Bitmap> lstFotos;
    private List<Uri> lstUrisFotos;

    // mainLayout is the child of the HorizontalScrollView ...
    private LinearLayout mainLayout;
    private View cell;
    private ViewPager viewPager;
    private String direccionIncidencia;

    class ParBitmapUri {
        private Bitmap bitmap;
        private Uri uriFoto;

        ParBitmapUri(Bitmap bitmap, Uri uriFoto) {
            this.bitmap = bitmap;
            this.uriFoto = uriFoto;
        }

        Bitmap getBitmap() {
            return bitmap;
        }

        Uri getUriFoto() {
            return uriFoto;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon_ciudadano);

        gps = new Gps(this);
        gps.registrarLocationManager(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Integer numeroFotos = (Integer) getIntent().getExtras().get(NUM_FOTOS);
            if (numeroFotos != null) {
                numFotosPermitidas = numeroFotos;
            }
        }

        lstFotos = new ArrayList<>();
        lstUrisFotos = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.libCiuViewPager);
        mainLayout = (LinearLayout) findViewById(R.id.libCiudLinearGallery);

        final RadioGroup opcionesRecogidaResiduos = (RadioGroup)findViewById(R.id.libCiuRadioGroupOpcionesRecogidaResiduos);
        RadioGroup grupoOpciones = (RadioGroup)findViewById(R.id.libCiuRadioGroupOpciones);
        grupoOpciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int visibilidadOpcionesRecogidaResiduos = View.GONE;
                if(checkedId == R.id.libCiuOpcRecogidaResiduos) {
                    visibilidadOpcionesRecogidaResiduos = View.VISIBLE;
                }
                opcionesRecogidaResiduos.setVisibility(visibilidadOpcionesRecogidaResiduos);
            }
        });

        direccionIncidencia = null;
    }

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

    private boolean isSacarFotoPermitido() {
        return lstFotos.size() < numFotosPermitidas;
    }

    private String getNombreFoto() {
        String resul = "FotoBuzonCiudadano_{0}";

        return MessageFormat.format(resul,contadorFotos++);
    }

    public void sacarFoto(View view) {
        if (!isSacarFotoPermitido()) {
            Toast.makeText(this, R.string.numFotosMaxAlcanzado, Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            File photo;
            try {
                ItfAlmacenamiento almacenamiento = AlmacenamientoUtilFactory.getAlmacenamiento(this);
                String nombreFichero = getNombreFoto();
                // place where to store camera taken picture
                photo = almacenamiento.crearFicheroTemporal(nombreFichero, ".jpg");
                photo.delete();
                fotoUri = Uri.fromFile(photo);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            } catch(Exception e) {
                Log.v(TAG, "No se ha podido crear fichero para sacar la foto");
                Toast.makeText(this, R.string.txtCiuMsjRevisaTarjeta, Toast.LENGTH_LONG).show();
            }

            startActivityForResult(i, FOTO_PARA_BUZON_CIUDADANO);
        }
    }

    private void addFotoGaleria(Intent data) {

        //Bundle extras = data.getExtras();
        //Bitmap imageBitmap = (Bitmap) extras.get("data");
        File file = new File(fotoUri.getPath());
        try {
            FileInputStream fis = new FileInputStream(file);
            Bitmap imageBitmap = BitmapFactory.decodeStream(fis);
            UtilImagenes utilImagenes = new UtilImagenes();
            imageBitmap = utilImagenes.escalar(imageBitmap, 600);

        //mImageView.setImageBitmap(imageBitmap);
            lstFotos.add(imageBitmap);
            lstUrisFotos.add(fotoUri);

            cell = getLayoutInflater().inflate(R.layout.celda_galeria, null);
            final ImageView imageView = (ImageView) cell.findViewById(R.id.libCiuImagen);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.setAdapter
                            (new GalleryPagerAdapter(BuzonCiudadanoActivity.this, lstFotos));
                    viewPager.setCurrentItem(v.getId());
                }
            });

            final ImageView imageViewBorrar = (ImageView) cell.findViewById(R.id.libCiuBorrarImagen);
            ParBitmapUri bitmapUri = new ParBitmapUri(imageBitmap, fotoUri);
            Pair<View, ParBitmapUri> celdaUri = new Pair<>(cell, bitmapUri);
            imageViewBorrar.setTag(celdaUri);

            imageView.setFitsSystemWindows(true);
            imageView.setImageBitmap(imageBitmap);

            mainLayout.addView(cell);
        } catch(Exception e) {
            Toast.makeText(this, "Problema al sacar la foto.", Toast.LENGTH_LONG).show();
        }
    }

    public void borrarImagen(View view) {
        Pair<View, ParBitmapUri> celdaUri = (Pair) view.getTag();
        View celda = celdaUri.first;
        ParBitmapUri bitmapUri = celdaUri.second;
        Bitmap bitmap = bitmapUri.getBitmap();
        mainLayout.removeView(celda);
        lstFotos.remove(bitmap);
        Uri fotoUriTmp = bitmapUri.getUriFoto();
        borrarFoto(fotoUriTmp);;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FOTO_PARA_BUZON_CIUDADANO) {
                //Toast.makeText(this, "Imagen guardada en: " + fotoUri.toString(), Toast.LENGTH_LONG).show();
                addFotoGaleria(data);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.acionCanceladaUsuario, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.errorCapturaFoto, Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        borrarFotos();
    }

    @Override
    public void actualizacionPosicion(Location locationActual) {
        TextView tv = (TextView) findViewById(R.id.libCiuDireccion);
        tv.setText("Nueva localizacion: " + locationActual.getLatitude() + ", " + locationActual.getLongitude());
        GoogleMaps gm = new GoogleMaps();
        gm.conseguirDireccion(locationActual, this);
    }

    @Override
    public void direccionFromLocation(String direccion) {
        TextView tv = (TextView) findViewById(R.id.libCiuDireccion);
        tv.setText(direccion);
        Toast.makeText(this, "La direccion calculada es " + direccion, Toast.LENGTH_SHORT).show();
        direccionIncidencia = direccion;
    }

    private void consultaDireccionUsuario() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BuzonCiudadanoActivity.this);
        alertDialog.setTitle(R.string.lblCiuSinDireccion);
        alertDialog.setMessage(R.string.lblCiuEscribirDireccion);

        final EditText input = new EditText(BuzonCiudadanoActivity.this);
        input.setSingleLine(false);
        input.setLines(4);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(R.string.lblCiuAceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        direccionIncidencia = input.getText().toString();
                        dialog.dismiss();
                        enviar();
                    }
                });

        alertDialog.setNegativeButton(R.string.lblCiuCancelar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Resources resources = BuzonCiudadanoActivity.this.getResources();
                        direccionIncidencia = resources.getString(R.string.txtCiuUsuarioNoIndicaDireccion);
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void enviar() {
        Log.d(TAG, "Se realiza el envio del correo.");

        final MailSender mailSender = new MailSender();

        mailSender.setLstUrisFotos(lstUrisFotos);

        mailSender.setDireccion(direccionIncidencia);

        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Pattern patronNumTelef = Patterns.PHONE;
        String mPhoneNumber = tMgr.getLine1Number();

        final Dialog myDialog = new Dialog(BuzonCiudadanoActivity.this);
        myDialog.setContentView(R.layout.mostrar_envio_correo);
        Button btnAceptar = (Button)myDialog.findViewById(R.id.libCiuDialogBtnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskMailSender taskMailSender = new AsyncTaskMailSender(BuzonCiudadanoActivity.this, mailSender);
                taskMailSender.execute((Void) null);

                myDialog.hide();
            }
        });
        Button btnCancelar = (Button)myDialog.findViewById(R.id.libCiuDialogBtnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.hide();
            }
        });

        TextView libCiuMostrarEnvioTxtDireccion = (TextView) myDialog.findViewById(R.id.libCiuMostrarEnvioTxtDireccion);
        libCiuMostrarEnvioTxtDireccion.setText(direccionIncidencia);

        myDialog.setTitle(R.string.comentario);

        if (patronNumTelef.matcher(mPhoneNumber).matches()) {
            TextView libCiuMostrarEnvioTxtTelefono = (TextView) myDialog.findViewById(R.id.libCiuMostrarEnvioTxtTelefono);
            libCiuMostrarEnvioTxtTelefono.setText(mPhoneNumber);
            mailSender.setTelefono(mPhoneNumber);
        }

        // Direciones de correo
        Pattern patronDirCorreo = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (patronDirCorreo.matcher(account.name).matches()) {
                String dirCorreo = account.name;
                TextView libCiuMostrarEnvioTxtCorreo = (TextView) myDialog.findViewById(R.id.libCiuMostrarEnvioTxtCorreo);
                libCiuMostrarEnvioTxtCorreo.setText(dirCorreo);
                mailSender.setCorreo(dirCorreo);
                break;
            }
        }

        TextView libCiuMostrarEnvioComentario = (TextView) myDialog.findViewById(R.id.libCiuMostrarEnvioComentario);
        EditText editComentario = (EditText)findViewById(R.id.libCiuTextComentario);
        if(editComentario.getVisibility() == View.VISIBLE) {
            libCiuMostrarEnvioComentario.setText(editComentario.getText());
            mailSender.setComentario(editComentario.getText().toString());
        }

        RadioGroup rgTipo = (RadioGroup)findViewById(R.id.libCiuRadioGroupOpciones);
        int idTipoSeleccionado = rgTipo.getCheckedRadioButtonId();
        if(idTipoSeleccionado > 0) {
            RadioButton rbTipo = (RadioButton) findViewById(idTipoSeleccionado);
            if(rbTipo != null) {
                mailSender.setTipo(rbTipo.getText().toString());
            }
        }

        myDialog.show();

    }

    private void pantallaAnterior() {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
    }

    public void enviar(View view) {
        try {
            if (direccionIncidencia == null) {
                consultaDireccionUsuario();
            } else {
                enviar();
            }
        } catch(Exception e) {
            Log.e(TAG, "Error al enviar el correo.", e);
        }
    }


    @Override
    public void onBackPressed() {
        if (viewPager != null && viewPager.isShown()) {
            viewPager.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    private void visibilidadBarraProgreso(final int visibilidad) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProgressBar rbTipo = (ProgressBar) findViewById(R.id.barraProgreso);
                    rbTipo.setVisibility(visibilidad);
                } catch (Exception e) {
                    Log.e(TAG, "Error al ocultar la barra de progreso.", e);
                }
            }
        });
    }

    private void mostrarToast(final int idMensaje, final int tiempo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BuzonCiudadanoActivity.this, idMensaje, tiempo).show();
            }
        });
    }

    @Override
    public void inicioEnvio() {
        try {
            visibilidadBarraProgreso(View.VISIBLE);
        } catch(Exception e) {
            Log.e(TAG, "Error al actuar por el inicio del envio.", e);
        }
    }

    @Override
    public void envioCorrecto() {
        try {
            visibilidadBarraProgreso(View.GONE);
            mostrarToast(R.string.txtCiuMsjOK, Toast.LENGTH_LONG);
            pantallaAnterior();
        } catch(Exception e) {
            Log.e(TAG, "Error al confirmar el envio correcto.", e);
        }
    }

    @Override
    public void envioErroneo() {
        try {
            visibilidadBarraProgreso(View.GONE);
            mostrarToast(R.string.txtCiuMsjKO, Toast.LENGTH_LONG);
            pantallaAnterior();
        } catch(Exception e) {
            Log.e(TAG, "Error al confirmar el envio erroneo.", e);
        }
    }
}
