import ru.ifmo.se.pokemon.*;

public class Amaura extends Pokemon {
    public Amaura(String name, int level) {
        super(name, level);
        setStats(77,59,50,67,63,46);
        setType(Type.ROCK, Type.ICE);
        setMove(new DoubleTeam(), new RockPolish(), new Bulldoze());
    }
}