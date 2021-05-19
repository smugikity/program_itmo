package commands;

import server.ServerCommandReader;

import java.util.Collection;
import java.util.HashMap;

public class CommandHelp extends Command {
    private HashMap<String, Command> cms;

    public CommandHelp(String des, HashMap<String, Command> cms) {
        setDescription(des);
        this.cms = cms;
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