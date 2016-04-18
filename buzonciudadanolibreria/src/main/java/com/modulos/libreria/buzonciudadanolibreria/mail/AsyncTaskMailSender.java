package com.modulos.libreria.buzonciudadanolibreria.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by h on 12/10/15.
 */
public class AsyncTaskMailSender extends AsyncTask<Void, Void, AsyncTaskMailSender.ResultadoEnvioMail> {
    private final static String TAG = "AsyncTaskMailSender";
    private MailSenderListener mailSenderListener;

    enum ResultadoEnvioMail {OK, KO};

    private MailSender mailSender;

    public interface MailSenderListener {
        void inicioEnvio();
        void envioCorrecto();
        void envioErroneo();
    }

    public AsyncTaskMailSender(MailSenderListener mailSenderListener, MailSender gmailSender) {
        this.mailSenderListener = mailSenderListener;
        this.mailSender = gmailSender;
    }

    @Override
    protected ResultadoEnvioMail doInBackground(Void... params) {
//        GMailSender gmailSender = new GMailSender("primosapps@gmail.com", "cacharreando2014");
        try {
            Log.d(TAG, "Se realiza el envio de un correo");
            mailSenderListener.inicioEnvio();
            mailSender.enviar();
            Log.d(TAG, "Correo enviado, quizas correctamente.");
            return ResultadoEnvioMail.OK;
        } catch(Exception e) {
            Log.e(TAG, "Error enviando el correo.", e);
            return ResultadoEnvioMail.KO;
        }
    }

    @Override
    protected void onPostExecute(ResultadoEnvioMail result) {

        try {
            if (result == ResultadoEnvioMail.OK) {
                mailSenderListener.envioCorrecto();
            } else {
                mailSenderListener.envioErroneo();
            }
        } catch(Exception e) {
            Log.e(TAG, "Error onPostExecute.", e);
        }
    }

}
