package commands;

import server.ServerReader;

import java.util.Collection;
import java.util.HashMap;

public class CommandHelp extends Command {
    private HashMap<String, Command> cms;

    public CommandHelp(ServerReader serverReader, String des, HashMap<String, Command> cms) {
        super(serverReader);
        setDescription(des);
        this.cms = cms;
    }
    @Override
    public String execute(Collection<Command> avaicm) {
        String result="";
        for (Command cm:avaicm) {
            result += cm.getDescription()+"\n";
        }
        return (result);
    }
}