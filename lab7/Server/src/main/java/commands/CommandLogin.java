package commands;

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
    public synchronized String execute(String data) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return invalidArguments;
        try (Connection connection = ServerReader.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM USERS WHERE EMAIL=? AND PASS=?");
            preparedStatement.setString(1,splitedData[0]);
            preparedStatement.setString(2,Hashing.hashSHA384(splitedData[1]));
            try (ResultSet answer = preparedStatement.executeQuery()) {
                int userID = 0;
                while (answer.next()) userID = answer.getInt(1);
                if (userID != 0) {
                    login(userID);
                    return "Login successfully";
                } else return "Email or password not match.";
            }
        } catch (SQLException throwables) {
            return sqlException;
        }
    }
    private void login(int id) {

    }
}
