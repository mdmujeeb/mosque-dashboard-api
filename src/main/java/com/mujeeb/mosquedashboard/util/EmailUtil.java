package com.mujeeb.mosquedashboard.util;

import java.util.Properties;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtil {
    public static boolean sendEMail() {
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
      properties.setProperty("mail.user", "mujeeb@alithistech.com");
      properties.setProperty("mail.password", "Mummy@123");

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("This is the Subject Line!");

         // Send the actual HTML message, as big as you like
         message.setContent("<h1>This is actual message</h1>", "text/html");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
         return true;
      } catch (MessagingException mex) {
         mex.printStackTrace();
         return false;
      }
    }
}