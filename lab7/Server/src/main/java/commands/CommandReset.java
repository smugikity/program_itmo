package commands;

import datapack.Pack;
import datapack.StringPack;
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
    public Pack execute(String data, ServerCommandReader caller) {
        String[] splitedData = data.split(",");
        if (splitedData.length!=2) return new StringPack(false,invalidArguments);
        if (!splitedData[0].equals(caller.getActivationCode())) return new StringPack(false,"Code is not correct");
        caller.setActivationCode("");
        try (Connection connection = ServerReader.getInstance().getConnection() ) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("UPDATE USERS SET PASS=? WHERE ID=?;");
            statement.setInt(2,caller.getID());
            statement.setString(1, Hashing.hashSHA384(splitedData[1]));
            if (statement.executeUpdate()>0) {
                connection.commit();
                return new StringPack(true,"Password reset successfully");
            } else {
                connection.rollback();
                return new StringPack(false,"Failed, please try again later");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new StringPack(false,sqlException);
        }
    }
}
