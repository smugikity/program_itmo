package commands;

public class CommandExit extends Command {
    public CommandExit(String des) {
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
