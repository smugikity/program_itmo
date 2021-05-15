package commands;

import server.ServerReader;

public class CommandReset extends Command {
    public CommandReset(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute(String data) {
        return ("");
    }
}
