import ru.ifmo.se.pokemon.*;

public class Bouffalant extends Pokemon {
    public Bouffalant(String name, int level) {
        super(name, level);
        setStats(95,110,95,40,95,55);
        setType(Type.NORMAL);
        setMove(new ScaryFace(), new Facade(), new Amnesia(), new SwordsDance());
    }
}