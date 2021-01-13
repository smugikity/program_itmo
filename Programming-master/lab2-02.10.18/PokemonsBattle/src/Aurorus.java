import ru.ifmo.se.pokemon.*;

public class Aurorus extends Amaura {
    public Aurorus(String name, int level) {
        super(name, level);
        setStats(123,77,72,99,92,58);
        setType(Type.ROCK, Type.ICE);
        setMove(new DoubleTeam(), new RockPolish(), new Bulldoze(), new Psychic());
    }
}