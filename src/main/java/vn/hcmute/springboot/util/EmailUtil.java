package vn.hcmute.springboot.util;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public final class EmailUtil {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private EmailUtil() { }

    public static void sendOtp(String toEmail, String otp, String purpose) throws MessagingException {
        String username = getConfig("MAIL_USERNAME", "mail.username");
        String password = getConfig("MAIL_PASSWORD", "mail.password");
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new MessagingException("Chưa cấu hình MAIL_USERNAME và MAIL_PASSWORD.");
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        Session mailSession = Session.getInstance(properties, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Mã OTP - " + purpose, "UTF-8");
        message.setText(
                "Mã OTP của bạn là: " + otp
                + "\nMã có hiệu lực trong 5 phút."
                + "\nKhông chia sẻ mã này cho người khác.",
                "UTF-8");
        Transport.send(message);
    }

    private static String getConfig(String environmentName, String propertyName) {
        String value = System.getenv(environmentName);
        if (value == null || value.isBlank()) value = System.getProperty(propertyName);
        return value;
    }
}
