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

    private MailSender mailSender;

    public AsyncTaskMailSender(MailSender gmailSender) {
        this.mailSender = gmailSender;
    }

    @Override
    protected ResultadoEnvioMail doInBackground(Void... params) {
//        GMailSender gmailSender = new GMailSender("primosapps@gmail.com", "cacharreando2014");
        try {
            Log.d(TAG, "Se realiza el envio de un correo");
            mailSender.enviar();
            Log.d(TAG, "Correo enviado, quizas correctamente.");
            return ResultadoEnvioMail.OK;
        } catch(Exception e) {
            Log.e("[AsyncTaskMailSender]", "Error enviando el correo.", e);
            return ResultadoEnvioMail.KO;
        }
    }

    @Override
    protected void onPostExecute(ResultadoEnvioMail result) {
        Log.d(TAG, "Resultado del envio del correo: " + result);
    }

}
