package commands;

import server.ServerCommandReader;

import java.util.Collection;

public interface Executor {
    String execute();
    String execute(Collection<Command> avaicm);
    String execute(String arg);
    String execute(String arg, ServerCommandReader caller);
    String execute(ServerCommandReader caller);
    String execute(Collection<Command> arg, ServerCommandReader caller);
}
