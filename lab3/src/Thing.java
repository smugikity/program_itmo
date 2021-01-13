import java.util.Objects;

public class Thing extends Object{
    public Thing(String name){
        this.setName(name);
    }
    public String MoveAsString(Moves move, Adverbs adverb){
        return (this.getName()+move.getDes()+adverb.getDes());
    }
    @Override
    public String toString() {
        return "Name: " + this.getName();
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Thing)) return false;
        Thing thing = (Thing) o;
        return Objects.equals(this.getName(), thing.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
