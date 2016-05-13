package com.modulos.libreria.utilidadeslibreria.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by h on 2/05/16.
 */
public class UtilImagenes {
    private final static String TAG = "UtilImagenes";

    /**
     * Escala la imagen a un ancho maximo manteniendo la proporcion.
     *
     * @param bm
     * @param anchoMaximo
     * @return
     */
    public Bitmap escalar(Bitmap bm, int anchoMaximo) {
        if (bm.getWidth() > anchoMaximo) {
            float escala = ((float) (bm.getWidth()) / anchoMaximo);
            int ancho = (int) (bm.getWidth() / escala);
            int alto = (int) (bm.getHeight() / escala);
            bm = Bitmap.createScaledBitmap(bm, ancho, alto, false);
        }
        return bm;
    }
}
