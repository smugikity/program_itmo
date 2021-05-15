package commands;

public class CommandReset extends Command {
    public CommandReset(String des) {
        setDescription(des);
    }
    @Override
    public String execute(String data) {
        return ("");
    }
}
