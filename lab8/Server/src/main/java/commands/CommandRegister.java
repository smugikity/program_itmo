package commands;

import server.ServerCommandReader;
import server.ServerReader;
import ultility.Hashing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandRegister extends Command{
    public CommandRegister(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data, ServerCommandReader caller) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return invalidArguments;
        try (Connection connection = ServerReader.getInstance().getConnection() ) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (EMAIL, PASS) SELECT ?, ? " +
                    "WHERE NOT EXISTS (SELECT EMAIL FROM USERS WHERE EMAIL=?);");
        statement.setString(1,splitedData[0]);
        statement.setString(3,splitedData[0]);
        statement.setString(2, Hashing.hashSHA384(splitedData[1]));
        if (statement.executeUpdate()>0) {
            connection.commit();
            return "Registered successfully";
        } else {
            connection.rollback();
            return "Email existed. Please login";
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return sqlException;
        }
    }
}
