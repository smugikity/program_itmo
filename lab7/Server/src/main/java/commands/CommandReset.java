package commands;

import main.ServerCommandReader;
import main.ServerReader;
import ultility.Hashing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandReset extends Command {
    public CommandReset(String des) {
        setDescription(des);
    }
    @Override
    public String execute(String data, ServerCommandReader caller) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return invalidArguments;
        if (!splitedData[0].equals(caller.getActivationCode())) return "Code is not correct\0";
        caller.setActivationCode("");
        try (Connection connection = ServerReader.getInstance().getConnection() ) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("UPDATE USERS SET PASS=? WHERE ID=?;");
            statement.setInt(2,caller.getID());
            statement.setString(1, Hashing.hashSHA384(splitedData[1]));
            if (statement.executeUpdate()>0) {
                connection.commit();
                return "Password reset successfully\1";
            } else {
                connection.rollback();
                return "Failed, please try again later\0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return sqlException;
        }
    }
}
