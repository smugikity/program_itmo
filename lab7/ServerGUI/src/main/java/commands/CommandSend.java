package commands;

import main.ServerCommandReader;
import main.ServerReader;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class CommandSend extends Command {
    public CommandSend(String des) {
        setDescription(des);
    }
    @Override
    public String execute(String data, ServerCommandReader caller) {
        try (Connection connection = ServerReader.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT ID FROM USERS WHERE EMAIL=?;");
            statement.setString(1,data);
            try (ResultSet answer = statement.executeQuery()) {
                int id = 0;
                while (answer.next()) id = answer.getInt(1);
                if (id != 0) {
                    caller.setID(id);
                } else return "Email not found. Register first";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return sqlException;
        }
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mail.properties")) {
            Properties prop = System.getProperties();
            prop.load(inputStream);
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(prop.getProperty("sender_email"), prop.getProperty("sender_pass"));
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("sender_email")));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(data));
            message.setSubject("Activation code");
            String code = generateCode();
            caller.setActivationCode(code);
            message.setText(code);
            Transport.send(message);
            return "Sent code. Code only work while client is still connected. \"reset\" to reset password.\1";
        } catch (IOException | NullPointerException | AddressException e) {
            e.printStackTrace();
            return "Error: IOException";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error: Messaging Exception";
        }
    }
    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 32; i++) result.append(chars.charAt(random.nextInt(chars.length())));
        return result.toString();
    }
}
