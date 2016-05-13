package com.modulos.libreria.utilidadeslibreria.almacenamiento;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AlmacenamientoUtilInterno implements ItfAlmacenamiento {
	private final static String TAG = "AlmacenamientoUtilInterno";

	private Context contexto;

	public AlmacenamientoUtilInterno(Context contexto) {
		this.contexto = contexto;
	}
	
	@Override
	public File crearFicheroTemporal(String part, String ext) throws Exception {
		File file = File.createTempFile(part, ext, contexto.getCacheDir());
		return file;

	}
}
