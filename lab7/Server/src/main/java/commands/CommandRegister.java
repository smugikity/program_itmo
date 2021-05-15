package commands;

import server.ServerReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandRegister extends Command{
    public CommandRegister(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        try (Connection connection = ServerReader.getInstance().getConnection() ) {
            PreparedStatement statement ;
        } catch (SQLException e) {
            e.printStackTrace();
            return "SQL error";
        }
        return ("");
    }
}
