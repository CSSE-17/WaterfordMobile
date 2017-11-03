package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Configuration;

/**
 *
 * @author Mahendra Tennakoon
 */
public class EmailUtil {

    Properties props;
    String userName;
    String password;

    /**
     * Initialize fields.
     */
    public EmailUtil() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "456");

        Configuration conf = new Configuration();
        Properties emailAuth = conf.readEmailConfig();
        userName = emailAuth.getProperty("user_name");
        password = emailAuth.getProperty("password");
    }

    /**
     * Sends a password reset token to the given address using team email
     * account.
     *
     * @param username
     * @param email
     * @param reset_token
     */
    public boolean sendPasswordResetEmail(String username, String email, String reset_token) {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("User Account Recovery");
            message.setText("Dear " + username + ", \n\n A request to reset your password has been made.\n"
                    + "You may use the following token to reset your password.\n\n" + reset_token + "\n\n"
                    + "This token can only be used once and it will lead you to a page where you can reset your password."
                    + "\nIt expires after you close the password recovery wizard and nothing will happen if it's not used."
                    + "\n\nThank you,\nStore Management System - Dev Team");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
