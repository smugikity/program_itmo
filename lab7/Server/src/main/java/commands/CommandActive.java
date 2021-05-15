package commands;

public class CommandActive extends Command {
    public CommandActive(String des) {
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
