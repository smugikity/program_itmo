package commands;

import datapack.Pack;
import main.ServerCommandReader;

import java.util.Collection;

public interface Executor {
    Pack execute();
    Pack execute(Collection<Command> avaicm);
    Pack execute(String arg);
    Pack execute(String arg, ServerCommandReader caller);
    Pack execute(ServerCommandReader caller);
    Pack execute(Collection<Command> arg, ServerCommandReader caller);
}
