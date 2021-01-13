import java.util.Objects;
import java.util.Random;

public class Human extends Object implements Generators{
    public static Human[][] ListofTargets = new Human[Positions.values().length][20];
    private Positions Target;
    public Human(){
        generateName();
        generatetarget();
        //System.out.println(this.getName()+" "+this.getTarget()+" "+this);
    }
    public Human(String name, Positions target) {
        this.setName(name);
        this.setTarget(target);
    }
    public Human(String name) {
        this.setName(name);
        generatetarget();
    }
    public void Wakeup(boolean did, String when) {
        System.out.println(this.getName()+((did)?"did":"didn't even") +" wake up when " + when);
    }
    private void append(int index, Human value){
        int i;
        for (i=0;i<ListofTargets[index].length;i++){
            if (ListofTargets[index][i] == null) {break;}
        }
        ListofTargets[index][i] = value;
    }
    public void synchronizedmove(){
        System.out.println("Accidents and coincidences work wonders. Knowing nothing about each other and about each other's adventures");
        for (int i=0;i<ListofTargets.length;i++) {
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < ListofTargets[i].length; j++) {
                if (ListofTargets[i][j] != null) {
                    result.append((j == 0) ? "" : " and ").append(ListofTargets[i][j].getName());
                }
            }
            if (Positions.values()[i]==Positions.BAY) {
                System.out.println(result + Moves.BE.getDes() + Positions.values()[i].getDes()+Adverbs.EVENING.getDes());
            }
            else if (!result.toString().equals("")) {
                System.out.println(result + Moves.BE.getDes() + Positions.values()[i].getDes());
            }
        }
    }
    final Random rand = new Random();
    @Override
    public  void generateName() {
        this.setName(Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)]+End[rand.nextInt(End.length)]);
    }
    @Override
    public void generatetarget() {
        this.setTarget(Positions.values()[(int)(Math.random()*Positions.values().length)]);
    }
    public Positions getTarget(){
        return Target;
    }
    public void setTarget(Positions target) {
        Target = target;
        append(Positions.valueOf(this.getTarget().name()).ordinal(),this);
    }
    @Override
    public String toString() {
        return "Name: " + this.getName() + "Target: " + this.getTarget();
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return Objects.equals(this.getName(), human.getName()) && Objects.equals(this.getTarget(),human.getTarget());
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getTarget());
    }
}
