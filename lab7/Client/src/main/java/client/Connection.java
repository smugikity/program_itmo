package client;

import controller.MainController;
import datapack.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab5.legacy.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Connection implements Runnable {
    public static Connection connection;
    public static Connection getInstance() {return connection;}
    private ObjectInputStream input;
    private PrintWriter output;
    private Queue<StringPack> queueStringPack = new LinkedList<>();
    private ObservableList<Person> people;
    private ObservableList<Person> filteredPeople = FXCollections.observableArrayList();
    private int filterMode = 0;
    private MainController main = null;
    public void setMain(MainController main) {
        this.main = main;
    }
    private int ID = 0;
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }
    public ObservableList<Person> getPeople() {
        return people;
    }
    public void setFilterMode(int i) {filterMode=i;}
    public int getFilterMode() {
        return filterMode;
    }

    //localhost, 6967
    public Connection(String host,int port) {
        try {
            connection = this;
            Socket socket = new Socket("localhost",port);
            input = new ObjectInputStream(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(),true);
            System.out.println(((StringPack) input.readObject()).toPrint());
            people = FXCollections.observableArrayList(((InitialMegaPack) input.readObject()).getPeople());
            people.stream().forEach(p-> System.out.println(p.toString()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public StringPack readStringPack() {
        return queueStringPack.poll();
    }

    public StringPack readForceStringPack() throws InterruptedException {
        StringPack pack;
        /*As I tested, if there is no breakpoint (sleep), program would crash due to the bombing of
        * read collection but no time to add at run method, but Im still not sure what exactly execution
        * that the sleep pause on, do they really also pause the run method (whole thread) or only current
        * method. Reexamined the process, seems that it dont execute the run method parallel to this method
        * (as it should be?) or the interruption of the new while is priority to the old one? Fuck java.  */
        while ((pack=Connection.getInstance().readStringPack())==null)
            TimeUnit.MILLISECONDS.sleep(100);
        return pack;
    }

    public void writeLine(String str) {
        output.println(str);
    }

    private char[] toChars(String str) {
        char[] ch = new char[str.length()];
        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        return ch;
    }

    private void handlePack(Pack pack) {
        if (pack instanceof AddPack) people.add(((AddPack) pack).getPerson());
        else if (pack instanceof UpdatePack) {
            people = FXCollections.observableArrayList(people.stream().filter(p->!p.getId().equals(((UpdatePack) pack).getId())).collect(Collectors.toList()));
            people.add(((UpdatePack) pack).getPerson());
        } else //if (pack instanceof RemovePack)
            people = FXCollections.observableArrayList(people.stream().filter(p->!((RemovePack) pack).getId().contains(p.getId())).collect(Collectors.toList()));
        setTableData(filterMode);
    }

    public void setTableData(int i) {
        /* mode
            -1: max_by_location
            0: normal (height cant < 0)
            >0: filter_less_than_height
             */
        filterMode = i;
        if (main != null) {
            if (filterMode == 0) filteredPeople.setAll(people);
            else if (filterMode == -1) {
                filteredPeople.clear();
                    filteredPeople.add(people.stream().max(Comparator.comparing(Person::getLocationValue)).get());
            } else
                filteredPeople = FXCollections.observableArrayList(people.stream().filter(p -> p.getHeight() < filterMode).collect(Collectors.toList()));
            main.getTableView().setItems(filteredPeople);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Read pack from Server");
                Pack pack;
                if ((pack=(Pack) input.readObject()) instanceof StringPack) {
                    if (main!=null)
                    main.getTextArea().setText(((StringPack) pack).toPrint());
                    queueStringPack.add((StringPack) pack);
                }
                else handlePack(pack);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
