package com.modulos.libreria.utilidadeslibreria.almacenamiento;

import android.Manifest;

import java.io.File;

public interface ItfAlmacenamiento {

	public static String[] permisosNecesarios = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

	File crearFicheroTemporal(String part, String ext) throws Exception;

}