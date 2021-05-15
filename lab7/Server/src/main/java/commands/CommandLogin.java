package commands;

import server.ServerReader;

public class CommandLogin extends Command{
    public CommandLogin(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        return ("");
    }
}
