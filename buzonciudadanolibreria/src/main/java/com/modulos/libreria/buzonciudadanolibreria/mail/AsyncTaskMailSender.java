package com.modulos.libreria.buzonciudadanolibreria.mail;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.MessagingException;

/**
 * Created by h on 12/10/15.
 */
public class AsyncTaskMailSender extends AsyncTask<Void, Void, AsyncTaskMailSender.ResultadoEnvioMail> {
    private final static String TAG = "AsyncTaskMailSender";

    enum ResultadoEnvioMail {OK, KO};

    private GMailSender gmailSender;

    public AsyncTaskMailSender(GMailSender gmailSender) {
        this.gmailSender = gmailSender;
    }

    @Override
    protected ResultadoEnvioMail doInBackground(Void... params) {
//        GMailSender gmailSender = new GMailSender("primosapps@gmail.com", "cacharreando2014");
        Log.d(TAG, "Se realiza el envio de un correo");
        try {
            gmailSender.sendMail("This is Subject",
                    "This is Body",
                    "primosapps@gmail.com",
                    "jfelixir@gmail.com");
        } catch (MessagingException e) {
            Log.e(TAG, "Error al enviar el correo", e);
            return ResultadoEnvioMail.KO;
        }
        Log.d(TAG, "Correo enviado, quizas correctamente.");
        return ResultadoEnvioMail.OK;
    }

    @Override
    protected void onPostExecute(ResultadoEnvioMail result) {
        Log.d(TAG, "Resultado del envio del correo: " + result);
    }

}
