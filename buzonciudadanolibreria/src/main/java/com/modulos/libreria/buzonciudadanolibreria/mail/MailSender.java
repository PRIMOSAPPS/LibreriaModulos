package com.modulos.libreria.buzonciudadanolibreria.mail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.mail.MessagingException;

/**
 * Created by h on 12/10/15.
 */
public class MailSender {
    private final static String TAG = "[MailSender]";

    private List<Uri> lstUrisFotos;
    private String telefono;
    private String direccion;
    private String correo;
    private String tipo;
    private String comentario;


//    public void enviarCorreo() throws MessagingException {
//        GMailSender sender = new GMailSender("primosapps@gmail.com", "cacharreando2014");
//        sender.sendMail("This is Subject",
//                "This is Body",
//                "user@gmail.com",
//                "user@yahoo.com");
//    }


    public void enviar() {
        Log.d(TAG, "Inicio enviar correo");

        //String fichero = "/home/h/Imágenes/redim/El cubo de rubik.jpg";

        //String strUrl = "http://localhost/dime/buzonciudadano/RecepcionAvisoCiudadano.php";
        String strUrl = "http://www.jamondemonesterio.org/dime/buzonciudadano/RecepcionAvisoCiudadano.php";
        String charset = "UTF-8";

        URL url;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(os));

            //////////////////////////////////////////
//            char[] imgBase64 = getFileCharArray(fichero);
            //////////////////////////////////////////

            // telefono
            // correo
            // direccion
            // imagen
            //		nombreImagen
            //		contenidoImagen


            char[] data = "<?xml version='1.0'?><contenidoIncidencia>".toCharArray();
            osw.write(data);
            if(telefono != null) {
                char[] dataTelefono = ("<telefono>"+telefono+"</telefono>").toCharArray();
                osw.write(dataTelefono);
            }
            if(correo != null) {
                char[] dataCorreo = ("<correo>"+correo+"</correo>").toCharArray();
                osw.write(dataCorreo);
            }
            if(direccion != null) {
                char[] dataDireccion = ("<direccion>"+direccion+"</direccion>").toCharArray();
                osw.write(dataDireccion);
            }
            if(tipo != null) {
                char[] dataTipo = ("<tipo>"+tipo+"</tipo>").toCharArray();
                osw.write(dataTipo);
            }
            if(comentario != null) {
                char[] dataComentario = ("<comentario>"+comentario+"</comentario>").toCharArray();
                osw.write(dataComentario);
            }

            char[] dataTagIniNombreImagen = "<nombreImagen>".toCharArray();
            char[] dataTagFinNombreImagen = "</nombreImagen>".toCharArray();
            char[] dataTagIniImagen = "<imagen>".toCharArray();
            char[] dataTagFinImagen = "</imagen>".toCharArray();
            char[] dataTagIniContenidoImagen = "<contenidoImagen>".toCharArray();
            char[] dataTagFinContenidoImagen = "</contenidoImagen>".toCharArray();
            char[] dataFin = "</contenidoIncidencia>".toCharArray();

            for(Uri uriFoto : lstUrisFotos) {
                osw.write(dataTagIniImagen);
                osw.write(dataTagIniNombreImagen);
                osw.write(uriFoto.toString().toCharArray());
                osw.write(dataTagFinNombreImagen);
                osw.write(dataTagIniContenidoImagen);
                osw.write(getFileCharArray(uriFoto));
                osw.write(dataTagFinContenidoImagen);
                osw.write(dataTagFinImagen);
            }
//            osw.write(dataTagIniImagen);
//            osw.write(dataTagIniNombreImagen);
//            osw.write("rubick1.jpg".toCharArray());
//            osw.write(dataTagFinNombreImagen);
//            osw.write(dataTagIniContenidoImagen);
//            osw.write(imgBase64);
//            osw.write(dataTagFinContenidoImagen);
//            osw.write(dataTagFinImagen);

//            osw.write(dataTagIniImagen);
//            osw.write(dataTagIniNombreImagen);
//            osw.write("rubick2.jpg".toCharArray());
//            osw.write(dataTagFinNombreImagen);
//            osw.write(dataTagIniContenidoImagen);
//            osw.write(imgBase64);
//            osw.write(dataTagFinContenidoImagen);
//            osw.write(dataTagFinImagen);

            osw.write(dataFin);
            osw.flush();
            osw.close();

            int respCode = urlConnection.getResponseCode();
            String respMess = urlConnection.getResponseMessage();

            Log.d(TAG, "Response code: " + respCode);
            Log.d(TAG, "Response message: " + respMess);
            InputStream is = urlConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder sb=new StringBuilder();
            BufferedReader br = new BufferedReader(isr);
            String read = br.readLine();

            while(read != null) {
                //System.out.println(read);
                sb.append(read);
                read =br.readLine();
            }

            Log.d(TAG, "Fin enviar correo, response: " + sb.toString());

        } catch (MalformedURLException e) {
            Log.e(TAG, "Error enviando correo.", e);
        } catch (IOException e) {
            Log.e(TAG, "Error enviando correo.", e);
        }
    }

    //private char[] getFileCharArray(String nombreFichero) throws IOException {
    private char[] getFileCharArray(Uri uriFoto) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        File file = new File(uriFoto.getPath());
        FileInputStream fis = new FileInputStream(file);

        Bitmap bm = BitmapFactory.decodeStream(fis);
        /*
        Bitmap bm1 = BitmapFactory.decodeStream(fis, null, options);


        int sampleSize = options.outHeight * options.outWidth;// * 2;
        options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        fis = new FileInputStream(file);
        Bitmap bm = BitmapFactory.decodeStream(fis, null, options);
        */


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bm.getWidth() > 800) {
            float escala = ((float)(bm.getWidth()) / 800);
            int ancho = (int)(bm.getWidth() / escala);
            int alto = (int)(bm.getHeight() / escala);
            bm = Bitmap.createScaledBitmap(bm, ancho, alto, false);
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encodedImage.toCharArray();




        /*
        File file = new File(nombreFichero);

        ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
        BufferedImage img=ImageIO.read(file);
        ImageIO.write(img, "jpg", baos);
        baos.flush();

        String base64String= Base64.encode(baos.toByteArray());
        baos.close();

        //////////////////////////////////////////////
//		byte[] bytearray = Base64.decode(base64String);
//
//		BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytearray));
//		ImageIO.write(imag, "jpg", new File("/home/h/Descargas","BORRAR.jpg"));

        return base64String.toCharArray();
        */
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setLstUrisFotos(List<Uri> lstUrisFotos) {
        this.lstUrisFotos = lstUrisFotos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}
