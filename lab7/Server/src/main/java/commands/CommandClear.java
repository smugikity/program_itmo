package commands;

public class CommandClear extends Command {
    public CommandClear(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute() {
        getCollection().clear();
        return ("Collection cleared");
    }
}
