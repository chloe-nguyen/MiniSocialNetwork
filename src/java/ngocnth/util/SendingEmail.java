package ngocnth.util;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingEmail implements Serializable {

    private String userEmail;
    private String verifyCode;

    public SendingEmail(String userEmail, String verifyCode) {
        this.userEmail = userEmail;
        this.verifyCode = verifyCode;
    }

    public void sendEmail() throws MessagingException {
        String myEmail = "cloudie1206@gmail.com";
        String myPassword = "0976874387";
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("Mini Social Network Email Verification Code");
        message.setText("Your Verification Code: " + verifyCode.toString());
        Transport.send(message);

    }
}
