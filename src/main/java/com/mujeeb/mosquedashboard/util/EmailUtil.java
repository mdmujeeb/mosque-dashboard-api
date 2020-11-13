package com.mujeeb.mosquedashboard.util;

import java.util.Properties;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtil {
    public static boolean sendEMail(String subject, String body) {
        // Recipient's email ID needs to be mentioned.
      String to = "mujeeb@alithistech.com";

      // Sender's email ID needs to be mentioned
      String from = "mujeeb@alithistech.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", "smtpout.secureserver.net");

      properties.setProperty("mail.smtp.auth", "true");
      properties.setProperty("mail.smtp.starttls.enable", "true");
      properties.setProperty("mail.smtp.socketFactory.port", "465");
      properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.setProperty("mail.smtp.auth", "true");
      properties.setProperty("mail.smtp.port", "465");

      properties.setProperty("mail.smtp.user", "mujeeb@alithistech.com");
      properties.setProperty("mail.smtp.password", "Mummy@123");


        // Get the default Session object.
      SmtpAuthenticator authenticator = new SmtpAuthenticator();
      Session session = Session.getDefaultInstance(properties, authenticator);

      try {
         // Create a default MimeMessage object.

         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Send the actual HTML message, as big as you like
         message.setContent(body, "text/html");

         // Send message
         Transport.send(message);
//         System.out.println("Sent message successfully....");
         return true;
      } catch (MessagingException mex) {
         mex.printStackTrace();
         return false;
      }
    }
}

class SmtpAuthenticator extends Authenticator {
    public SmtpAuthenticator() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "mujeeb@alithistech.com";
        String password = "Mummy@123";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}