package metaroa.traumimages.Mailer;

import metaroa.traumimages.Config.EmailConfig;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class MailingMechanism {
    public static void sendMai(
            String to,
            String subject,
            String content,
            String bodyContent
    )throws AddressException, MessagingException{
        Properties props = new Properties();
        EmailConfig emailConfig = new EmailConfig();
        props.put("mail.smtp.auth",emailConfig.getSmtpAuth());
        props.put("mail.smtp.starttls.enable",emailConfig.getStartTlsEnabled());
        props.put("mail.smtp.host",emailConfig.getHost());
        props.put("mail.smtp.port",emailConfig.getPort());
        Session session = Session.getInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailConfig.getEmail(),emailConfig.getPassword());
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailConfig.getEmail(),false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(content,"text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(bodyContent,"text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        Transport.send(msg);
    }
    public static void sendMail(
            String to,
            String subject,
            String content,
            String bodyContent,
            String fileToAttach
    ) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        EmailConfig emailConfig = new EmailConfig();
        props.put("mail.smtp.auth",emailConfig.getSmtpAuth());
        props.put("mail.smtp.starttls.enable",emailConfig.getStartTlsEnabled());
        props.put("mail.smtp.host",emailConfig.getHost());
        props.put("mail.smtp.port",emailConfig.getPort());
        Session session = Session.getInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailConfig.getEmail(),emailConfig.getPassword());
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailConfig.getEmail(),false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(content,"text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(bodyContent,"text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();
        attachPart.attachFile(fileToAttach);
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    public static String generateMessageBodyForEmailConfirm(
            String code,
            String name,
            String email,
            String domain,
            String siteName
    ){
        String link = domain + "confirm/" + code;
        String answer = String.format(
                "Dear %s; \n welcome to %s \n please click on the following link in order to confirm your email address" +
                "\n %s"+
                "If you find this email irrelevant, please just ignore it. \n"+
                "s%",name,link,name
        );
        return answer;
    }
}
