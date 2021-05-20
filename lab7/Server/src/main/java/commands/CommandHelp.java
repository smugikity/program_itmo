package commands;

import server.ServerCommandReader;

import java.util.Collection;

public class CommandHelp extends Command {

    public CommandHelp(String des) {
        setDescription(des);
    }
    @Override
    public String execute(Collection<Command> avaicm, ServerCommandReader caller) {
        String result="";
        for (Command cm:avaicm) {
            result += cm.getDescription()+"\n";
        }
        return (result);
    }
}