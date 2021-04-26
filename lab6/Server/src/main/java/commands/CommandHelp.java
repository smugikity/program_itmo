package commands;

import server.ServerReader;

import java.util.HashMap;

public class CommandHelp extends Command {
    private HashMap<String, Command> cms;
    public CommandHelp(ServerReader serverReader, String des, HashMap<String, Command> cms) {
        super(serverReader);
        setDescription(des);
        this.cms = cms;
    }
    @Override
    public String execute() {
        return ("help : display help for available commands\n" +
                "info : print information about the collection (type, date of initialization, number of elements, etc.) to standard output\n" +
                "show : print all collection items as string to standard output\n" +
                "add {element} : add a new element to the collection\n" +
                "update id {element} : update the value of the collection element whose id is equal to the given one\n" +
                "remove_by_id id : remove an item from the collection by its id\n" +
                "clear : clear the collection\n" +
                "save : save the collection to a file\n" +
                "execute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which the user enters them interactively.\n" +
                "exit : exit the program (without saving to file)\n" +
                "add_if_min {element} : add a new element to the collection if its value is less than the smallest element in this collection\n" +
                "remove_greater {element} : remove all elements from the collection that are greater than the specified one\n" +
                "history : display the last 14 commands (without their arguments)\n" +
                "max_by_location : display any object from the collection, the value of the location field of which is the maximum\n" +
                "group_counting_by_coordinates : group the elements of the collection by the value of the coordinates field, display the number of elements in each group\n" +
                "filter_less_than_height height : display elements whose height field value is less than the specified one");
    }
}
