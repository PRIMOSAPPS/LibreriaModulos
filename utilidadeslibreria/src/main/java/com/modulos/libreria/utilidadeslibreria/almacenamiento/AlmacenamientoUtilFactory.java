package com.modulos.libreria.utilidadeslibreria.almacenamiento;


import android.content.Context;

import com.modulos.libreria.utilidadeslibreria.preferencias.Preferencias;


public class AlmacenamientoUtilFactory {
	
	/**
	 * Devuelve el tipo de almacenamiento segun la inicializacion realizada en el primer arranque, en el que se decide
	 * si el almacenamiento sera interno o externo.
	 * @param contexto
	 * @return
	 */
	public static ItfAlmacenamiento getAlmacenamiento(Context contexto) {
		ItfAlmacenamiento resul = null;

		Preferencias preferencias = new Preferencias(contexto);
		if(preferencias.isAlmacenamientoInterno()) {
			resul = new AlmacenamientoUtilInterno(contexto);
		} else {
			resul = new AlmacenamientoUtilExterno();
		}

		return resul;
	}

}
