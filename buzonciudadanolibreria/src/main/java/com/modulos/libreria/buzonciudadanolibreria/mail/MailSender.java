package com.modulos.libreria.buzonciudadanolibreria.mail;

import javax.mail.MessagingException;

/**
 * Created by h on 12/10/15.
 */
public class MailSender {

    public void enviarCorreo() throws MessagingException {
        GMailSender sender = new GMailSender("primosapps@gmail.com", "cacharreando2014");
        sender.sendMail("This is Subject",
                "This is Body",
                "user@gmail.com",
                "user@yahoo.com");
    }
}
