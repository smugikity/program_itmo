package commands;

import main.ServerCommandReader;

public class CommandHistory extends Command {
    public CommandHistory(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        return "\1";
    }
}
