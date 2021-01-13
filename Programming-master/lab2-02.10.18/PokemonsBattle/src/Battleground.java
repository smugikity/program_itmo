import ru.ifmo.se.pokemon.*;

//zoomer zone

public class Battleground {
    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Bouffalant("Travis",1));
        field.addAlly(new Amaura("Kanye",3));
        field.addAlly(new Aurorus("Frank",3));
        field.addFoe(new Ralts("Kendrick",2));
        field.addFoe(new Kirlia("Tyler",3));
        field.addFoe(new Gallade("Abel",2));
        field.go();
    }
}