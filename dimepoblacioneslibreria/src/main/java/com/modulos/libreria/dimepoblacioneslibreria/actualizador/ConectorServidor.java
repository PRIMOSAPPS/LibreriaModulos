package com.modulos.libreria.dimepoblacioneslibreria.actualizador;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.modulos.libreria.dimepoblacioneslibreria.dto.CategoriaDTO;
import com.modulos.libreria.dimepoblacioneslibreria.dto.SitioDTO;
import com.modulos.libreria.dimepoblacioneslibreria.excepcion.DimeException;
import com.modulos.libreria.dimepoblacioneslibreria.util.Propiedades;
import com.modulos.libreria.dimepoblacioneslibreria.xml.EventosXML_SAX;
import com.modulos.libreria.utilidadeslibreria.util.UtilPropiedades;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class ConectorServidor {
	private final static String TAG = "[ConectorServidor]";
	private static String URL_GET_LISTA_CATEGORIAS = null;//"http://10.0.2.2/eventos/categorias/CategoriasToXML.php";
	private static String URL_GET_LISTA_SITIOS = null;//"http://10.0.2.2/eventos/sitios/SitiosToXML.php";

	public ConectorServidor(Context contexto) {
		cargarPropiedades();
	}
	
	/**
	 * Realiza la carga de las propiedades si no ha sido realizada anteriormente.
	 * Forma la URL a la que selocitar los datos de categorias, sitios y eventos.
	 */
	private void cargarPropiedades() {
		if(URL_GET_LISTA_CATEGORIAS == null) {
			UtilPropiedades propiedades = UtilPropiedades.getInstance();

			String servidor = propiedades.getProperty(Propiedades.PROP_SERVIDOR);
			String rutaCategorias = propiedades.getProperty(Propiedades.PROP_RUTA_CATEGORIAS_XML);
			String rutaSitios = propiedades.getProperty(Propiedades.PROP_RUTA_SITIOS_XML);

			URL_GET_LISTA_CATEGORIAS = servidor + rutaCategorias;
			URL_GET_LISTA_SITIOS = servidor + rutaSitios;
		}
		Log.d(TAG, "URL de las categorias para la actualizacion: " + URL_GET_LISTA_CATEGORIAS);
		Log.d(TAG, "URL de los sitios para la actualizacion: " + URL_GET_LISTA_SITIOS);
	}

	/**
	 * Realiza la peticion al servidor de las categorias con una fecha de ultima actualizacion posterior a la
	 * recibida como parametro. Recibe las categorias en XML y usa la clase EventosXML_SAX para convertir el
	 * XML en una lista de objetos CategoriaDTO.
	 * 
	 * @param ultimaActualizacion
	 * @return
	 * @throws DimeException
	 */
	public List<CategoriaDTO> getListaCategorias(long ultimaActualizacion) throws DimeException {
		HttpURLConnection urlConnection = null;
		try {
			Log.w(TAG, "Pidiendo la actualizacion de categorias para la ultimaActualizacion: " + ultimaActualizacion + " -- " + new Date(ultimaActualizacion));

			URL url = new URL(URL_GET_LISTA_CATEGORIAS);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream is = new BufferedInputStream(urlConnection.getInputStream());
			Uri.Builder ub = new Uri.Builder();
			ub.appendQueryParameter("ultima_actualizacion", Long.toString(ultimaActualizacion));
			String query = ub.build().getEncodedQuery();
			OutputStream os = urlConnection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(query);
			writer.flush();
			writer.close();
			os.close();

//			HttpClient httpclient = new DefaultHttpClient();
//			/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
//			HttpPost httppost = new HttpPost(URL_GET_LISTA_CATEGORIAS);
//
//			/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
//			//ANADIR PARAMETROS
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("ultima_actualizacion", Long.toString(ultimaActualizacion) ) );
//			/* Una vez anadidos los parametros actualizamos la entidad de httppost, esto quiere decir
//			 * en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor
//			 * envien los datos que hemos añadido
//			 */
//			httppost.setEntity(new UrlEncodedFormEntity(params));
//
//			/*Finalmente ejecutamos enviando la info al server*/
//			HttpResponse resp = httpclient.execute(httppost);
//			HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/
//
//			String text = EntityUtils.toString(ent);
//			Log.w(TAG, text);
//
//			InputStream is = new ByteArrayInputStream(text.getBytes());

			EventosXML_SAX meXml = new EventosXML_SAX();
			urlConnection.connect();
			return meXml.leerCategoriasXML(is);
		} catch (Exception e) {
			throw new DimeException("Error al realizar la peticion al servidor: " + e.getMessage(), e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}

	/**
	 * Realiza la peticion al servidor de los sitios con una fecha de ultima actualizacion posterior a la
	 * recibida como parametro. Recibe los sitios en XML y usa la clase EventosXML_SAX para convertir el
	 * XML en una lista de objetos Sitio.
	 * 
	 * @param ultimaActualizacion
	 * @param idsCategoriasActualizacion 
	 * @return
	 * @throws DimeException
	 */
	public List<SitioDTO> getListaSitios(long ultimaActualizacion, String idsCategoriasActualizacion) throws DimeException {
		HttpURLConnection urlConnection = null;
		try {
			Log.w(TAG, "Pidiendo la actualizacion de sitios para la ultimaActualizacion: " + ultimaActualizacion +
					" y categorias: " + idsCategoriasActualizacion);

			URL url = new URL(URL_GET_LISTA_SITIOS);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream is = new BufferedInputStream(urlConnection.getInputStream());
			Uri.Builder ub = new Uri.Builder();
			ub.appendQueryParameter("ultima_actualizacion", Long.toString(ultimaActualizacion));
			String query = ub.build().getEncodedQuery();
			OutputStream os = urlConnection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(query);
			writer.flush();
			writer.close();
			os.close();


//			HttpClient httpclient = new DefaultHttpClient();
//			/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
//			HttpPost httppost = new HttpPost(URL_GET_LISTA_SITIOS);
//
//			/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
//			//ANADIR PARAMETROS
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("ultima_actualizacion", Long.toString(ultimaActualizacion) ) );
//			String idPoblacion = UtilPropiedadesBuzonCiudadano.getInstance().getProperty(UtilPropiedadesBuzonCiudadano.PROP_ID_POBLACION);
//			params.add(new BasicNameValuePair("id_poblacion", idPoblacion ) );
//			if(idsCategoriasActualizacion != null) {
//				params.add(new BasicNameValuePair("ids_categorias", idsCategoriasActualizacion ) );
//			}
//			/* Una vez anadidos los parametros actualizamos la entidad de httppost, esto quiere decir
//			 * en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor
//			 * envien los datos que hemos añadido
//			 */
//			httppost.setEntity(new UrlEncodedFormEntity(params));
//
//			/*Finalmente ejecutamos enviando la info al server*/
//			HttpResponse resp = httpclient.execute(httppost);
//			HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/
//
//			String text = EntityUtils.toString(ent);
//			Log.w(TAG, text);
//
//			InputStream is = new ByteArrayInputStream(text.getBytes());

			EventosXML_SAX meXml = new EventosXML_SAX();
			return meXml.leerSitiosXML(is);
		} catch (Exception e) {
			throw new DimeException("Error al realizar la peticion al servidor: " + e.getMessage(), e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}

}
