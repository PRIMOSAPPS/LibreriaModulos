package com.modulos.libreria.dimepoblacioneslibreria.actualizador;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.modulos.libreria.dimepoblacioneslibreria.R;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.CategoriasDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dao.impl.SitiosDataSource;
import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.dimepoblacioneslibreria.excepcion.DimeException;
import com.modulos.libreria.dimepoblacioneslibreria.preferencias.PreferenciasPoblaciones;
import com.modulos.libreria.utilidadeslibreria.util.UtilConexion;
import com.modulos.libreria.utilidadeslibreria.util.UtilFechas;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Realiza la acualizacion de los contenidos que tienen nuevas versiones en el servidor. La comprobacion de estas
 * nuevas versiones se realiza en base a la fecha de ultima actualizacion.
 * Hereda de AsyncTask por que android no permite la ejecucion de las acciones necesarias desde el hilo principal de la aplicacion.
 * Por lo que he visto recomiendan heredar de esta clase, realiza la accion en segundo plano.
 * @author h
 *
 */
public class AsyncTaskActualizador extends AsyncTask<Void, Void, AsyncTaskActualizador.ResultadoActualizacion> {
	private final static String TAG = "AsyncTaskActualizador";
	/**
	 * Resultado de la accion en segundo plano, que indica si las actualizaciones se realizan correctamente
	 * o se produce un error durante la actualizacion.
	 * @author h
	 *
	 */
	enum ResultadoActualizacion {OK, KO};

	private Context contexto;
	private boolean mostrarToast;

	/**
	 * 
	 * @param contexto
	 * @param mostrarToast Indica si se debe mostrar un mensaje mediante la clase Toast con el resultado de la actualizacion.
	 */
	public AsyncTaskActualizador(Context contexto, boolean mostrarToast) {
		Log.i(TAG, "INICIO");
		this.contexto = contexto;
		this.mostrarToast = mostrarToast;
	}

	/**
	 * Realiza la actualizacion de las categorias, comprobando primero la mayor fecha de ultima actualizacion de las
	 * categorias almacenadas en la base de datos. Usa el ConectorServidor para conseguir la lista de categorias con
	 * una fecha de ultima actualizacion mas nueva y los pasa al Actualizador para que las almacene en la base de datos
	 * y las imagenes en el almacenamiento correspondiente.
	 * @throws DimeException
	 */
	private void actualizarCategorias() throws DimeException {
		ConectorServidor cs = new ConectorServidor(contexto);
		CategoriasDataSource dataSource = new CategoriasDataSource(contexto);
		try {
			dataSource.open();
			
			long ultimaActualizacion = dataSource.getUltimaActualizacion();
			Date dateUltimaActualizacion = UtilFechas.fechaToUTC(new Date(ultimaActualizacion));
			ultimaActualizacion = dateUltimaActualizacion.getTime();
	
			List<CategoriaDTO> lstCategorias = cs.getListaCategorias(ultimaActualizacion);
			Actualizador actualizador = new Actualizador(contexto);
			actualizador.actualizarCategorias(lstCategorias);
		} catch (ParseException e) {
			Log.e(TAG, "Excepcion al parsear la fecha para conseguir la ultima actualizacion en formato UTC: ", e);
		} finally {
			dataSource.close();
		}
	}

	/**
	 * Realiza la actualizacion de los sitios, comprobando primero la mayor fecha de ultima actualizacion de los
	 * sitios almacenados en la base de datos. Usa el ConectorServidor para conseguir la lista de sitios con
	 * una fecha de ultima actualizacion mas nueva y los pasa al Actualizador para que los almacene en la base de datos
	 * y las imagenes en el almacenamiento correspondiente.
	 * 
	 * @param idsCategoriasActualizacion 
	 * @throws DimeException
	 */
	private void actualizarSitios(String idsCategoriasActualizacion) throws DimeException {
		ConectorServidor cs = new ConectorServidor(contexto);
		SitiosDataSource dataSource = new SitiosDataSource(contexto);
		try {
			dataSource.open();
			
			long ultimaActualizacion = dataSource.getUltimaActualizacion();
			Date dateUltimaActualizacion = UtilFechas.fechaToUTC(new Date(ultimaActualizacion));
			ultimaActualizacion = dateUltimaActualizacion.getTime();

			List<SitioDTO> lstSitios = cs.getListaSitios(ultimaActualizacion, idsCategoriasActualizacion);
			Actualizador actualizador = new Actualizador(contexto);
			actualizador.actualizarSitios(lstSitios);

		} catch (ParseException e) {
			Log.e(TAG, "Excepcion al parsear la fecha para conseguir la ultima actualizacion en formato UTC: ", e);
		} finally {
			dataSource.close();
		}
	}

//	/**
//	 * Devuelve el String que contiene los identificadores de las categorias para actualizar solo por estas categorias.
//	 * Los identificadores estan separados por comas
//	 * Si no hay ninguna categoria seleccionada, se devuelve null
//	 * @return
//	 */
//	private String getActualizacionPorCategorias() {
//		String resul = "";
//		CategoriasDataSource dataSource = new CategoriasDataSource(contexto);
//		try {
//			dataSource.open();
//			List<Categoria> lstCategorias = dataSource.getAll();
//			
//			SharedPreferences ratePrefs = PreferenceManager
//	                .getDefaultSharedPreferences(contexto);
//			boolean actualizarPorCategorias = ratePrefs.getBoolean(PreferenciasActivity.PREFERENCIA_ACTUALIZAR_POR_CATEGORIAS, false);
//			if(actualizarPorCategorias) {
//				for(Categoria categoria : lstCategorias) {
//					String key = PreferenciasActivity.PREFIJO_PREFERENCIA_CATEGORIAS + categoria.getId();
//					boolean activada = ratePrefs.getBoolean(key, false);
//					if(activada) {
//						resul += ","+categoria.getId();
//					}
//				}
//			}
//			if(!resul.equals("")) {
//				resul = resul.substring(1);
//			} else {
//				resul = null;
//			}
//		} finally {
//			dataSource.close();
//		}
//		return resul;
//	}

	/**
	 * Estas son las acciones que por heredar de la clase AsyncTask se ejecutara en segundo plano.
	 */
	@Override
	protected ResultadoActualizacion doInBackground(Void... arg0) {
		try {
			if(UtilConexion.estaConectado(contexto)) {
				PreferenciasPoblaciones preferencias = new PreferenciasPoblaciones(contexto);
				String idsCategoriasActualizacion = preferencias.getActualizacionPorCategorias();
				actualizarCategorias();
				actualizarSitios(idsCategoriasActualizacion);
			}
		} catch (DimeException e) {
			Log.e(TAG, "Error leyendo la ultima actualizacion.", e);
			return ResultadoActualizacion.KO;
		}
		return ResultadoActualizacion.OK;
	}

	/**
	 * Accion que se realiza al finalizar la accion en segundo plano. En este caso mostrara un mensaje con la clase
	 * Toast si el objeto ha sido configurado para ello mediante el atributo mostrarToast
	 */
	@Override
	protected void onPostExecute(ResultadoActualizacion result) {
		super.onPostExecute(result);
		if(this.mostrarToast) {
			int resulActualizacion = R.string.actualizacion_ko;
			if(result == ResultadoActualizacion.OK) {
				resulActualizacion = R.string.actualizacion_ok;
			}
			Toast.makeText(contexto, resulActualizacion, Toast.LENGTH_SHORT).show();
		}

	}

}
