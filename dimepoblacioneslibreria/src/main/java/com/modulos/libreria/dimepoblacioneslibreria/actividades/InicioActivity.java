package com.modulos.libreria.dimepoblacioneslibreria.actividades;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.actualizador.Actualizador;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.dimepoblacioneslibreria.excepcion.DimeException;
import com.modulos.libreria.dimepoblacioneslibreria.xml.EventosXML_SAX;
import com.modulos.libreria.utilidadeslibreria.almacenamiento.ItfAlmacenamiento;
import com.modulos.libreria.utilidadeslibreria.permisos.Permisos;
import com.modulos.libreria.utilidadeslibreria.preferencias.Preferencias;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class InicioActivity extends AppCompatActivity {
    private final static String TAG = "[InicioActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        iniciar();
    }

    private void cargaInicial(Preferencias preferencias) {

        if(preferencias.isPrimerArranque()) {
            Log.d(TAG, "Aun no se ha realizado el primer arranque");
            try {
                // Se decide el modo de almacenamiento
                preferencias.asignarModoAlmacenamiento(this);

                Actualizador actualizador = new Actualizador(this);
                EventosXML_SAX meXml = new EventosXML_SAX();

                Resources resources = getResources();

                int idResourceCategorias = resources.getIdentifier("raw/categorias_xml",
                        "raw", getPackageName());
                InputStream is = this.getResources().openRawResource(idResourceCategorias);
                List<CategoriaDTO> lstCategorias = meXml.leerCategoriasXML(is);
                actualizador.actualizarCategorias(lstCategorias);
                is.close();
                Log.d(TAG, "Se ha realizado la carga desde fichero de " + lstCategorias.size() + " categorias");

                // Los sitios es probable que superen ficheros de 1MB, por eso se realiza la carga de esta manera
                int i=1;
                int idResource = resources.getIdentifier("raw/sitios_xml_" + i,
                        "raw", getPackageName());
                while(idResource != 0) {
                    is = this.getResources().openRawResource(idResource);
                    List<SitioDTO> lstSitios = meXml.leerSitiosXML(is);
                    actualizador.actualizarSitios(lstSitios);
                    is.close();
                    Log.d(TAG, "Se ha realizado la carga desde fichero de " + lstSitios.size() + " sitios");
                    i++;
                    idResource = resources.getIdentifier("raw/sitios_xml_" + i,
                            "raw", getPackageName());
                }

//                cargarBandos(resources);

                Log.d(TAG, "Se se marca el primer arranque como realizado");
                preferencias.marcarPrimarArranqueRealizado(this);
            } catch (ParserConfigurationException | SAXException | IOException | DimeException e) {
                Log.e(TAG, "Se ha producido un error durante la carga inicial de los datos.", e);
                Toast.makeText(this, "Se ha producido un error durante la carga inicial de los datos.", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void cargarBandos(Resources resources) throws IOException, ParserConfigurationException, SAXException {
//        BandosXML_SAX xmlSax = new BandosXML_SAX();
//
//        InputStream is = this.getResources().openRawResource(R.raw.bandos_xml);
//        List<BandoDTO> lstBandos = xmlSax.leerBandosXML(is);
//        ActualizadorBandos actualizador = new ActualizadorBandos(this);
//        actualizador.actualizarBandos(lstBandos);
//        is.close();
//    }

    protected void iniciar() {
        Log.d(TAG, "iniciar()");
        final Preferencias preferencias = new Preferencias(this);
        if(preferencias.isPrimerArranque()) {
            Thread thrInicio = new Thread("ThreadInicio") {

                @Override
                public void run() {
                    Log.d(TAG, "Se lanza la carga inicial.");

                    Permisos permisosUtil = new Permisos();
                    if(!permisosUtil.preguntarPermisos(InicioActivity.this, ItfAlmacenamiento.permisosNecesarios)) {
                        cargaInicial(preferencias);
                    }

                    Log.d(TAG, "Se lanza MainActivity.");

                    finInicioActivity();
                }

            };
            // Se lanza la carga inicial de los datos en segundo plano para que se pinte la pantalla
            thrInicio.start();
        } else {
            finInicioActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Permisos.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {

                Permisos permisos = new Permisos();
                boolean concedidos = permisos.checkSiPermisosConcedidos(permissions, grantResults);
                if(concedidos) {
                    final Preferencias preferencias = new Preferencias(this);
                    cargaInicial(preferencias);
                } else {
                    Toast.makeText(this, R.string.permisos_necesarios, Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void finInicioActivity() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
