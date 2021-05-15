package commands;

import server.ServerReader;

import java.sql.*;

public class CommandLogin extends Command{
    public CommandLogin(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return invalidArguments;
        try (Connection connection = ServerReader.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM USERS WHERE EMAIL=? AND PASS=?");
            preparedStatement.setString(1,splitedData[0]);

        } catch (SQLException throwables) {
            return sqlException;
        }
        return ("");
    }
}
