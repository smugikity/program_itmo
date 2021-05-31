package commands;

import datapack.Pack;
import datapack.StringPack;
import main.ServerCommandReader;

public class CommandLogout extends Command{
    public CommandLogout(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        logout(caller);
        return new StringPack(true,"Logout successfully");
    }

}
