package commands;

public class CommandHistory extends Command {
    public CommandHistory(String des) {
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
