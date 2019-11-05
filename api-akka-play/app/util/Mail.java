package util;
import com.typesafe.config.Config;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.BodyPart;

public class Mail {

    public static void enviarMail(String subject,String contenido,String MailReceptor) {

        final String username = "mail.smtp.useremail";
        final String password = "mail.smtp.pass";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "mail.smtp.auth");
        props.put("mail.smtp.starttls.enable", "mail.smtp.starttls.enable");
        props.put("mail.smtp.host", "mail.smtp.host");
        props.put("mail.smtp.port", "mail.smtp.port");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(MailReceptor));
            message.setSubject(subject);
            message.setText(contenido);

            message.setContent(contenido, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}