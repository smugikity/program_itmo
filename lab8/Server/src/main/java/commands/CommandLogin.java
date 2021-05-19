package commands;

import server.Server;
import server.ServerCommandReader;
import server.ServerReader;
import ultility.Hashing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandLogin extends Command{
    public CommandLogin(String des) {
        setDescription(des);
    }
    @Override
    public String execute(String data, ServerCommandReader caller) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return invalidArguments;
        try (Connection connection = ServerReader.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM USERS WHERE EMAIL=? AND PASS=?;");
            preparedStatement.setString(1,splitedData[0]);
            preparedStatement.setString(2,Hashing.hashSHA384(splitedData[1]));
            try (ResultSet answer = preparedStatement.executeQuery()) {
                int id = 0;
                while (answer.next()) id = answer.getInt(1);
                if (id != 0) {
                    if (!Server.getClients().contains(id)) {
                        login(id, caller);
                        return "Login successfully";
                    } else return "Account is logged in elsewhere.";
                } else return "Email or password not match.";
            }
        } catch (SQLException throwables) {
            return sqlException;
        }
    }

}
