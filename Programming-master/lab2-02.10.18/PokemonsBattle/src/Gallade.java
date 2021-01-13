import ru.ifmo.se.pokemon.*;

public class Gallade extends Kirlia {
    public Gallade(String name, int level) {
        super(name, level);
        setStats(68,125,65,65,115,80);
        setType(Type.PSYCHIC, Type.FIGHTING);
        setMove(new ShadowBall(), new ThunderWave(), new DisarmingVoice(), new FocusBlast());
    }
}