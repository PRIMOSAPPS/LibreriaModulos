package com.modulos.libreria.utilidadeslibreria.almacenamiento;

import java.io.File;

public interface ItfAlmacenamiento {

	File crearFicheroTemporal(String part, String ext) throws Exception;

}