public abstract class Object implements Actions {
    private String Name;
    @Override
    public void Move(Moves move){
        System.out.println(this.getName()+move.getDes());
    }
    @Override
    public void Move(Moves move, Positions position){
        System.out.println(this.getName()+move.getDes()+position.getDes());
    }
    @Override
    public void Move(Moves move, Adverbs adverb){
        System.out.println(this.getName()+move.getDes()+adverb.getDes());
    }
    @Override
    public void Move(Moves move, Positions position, Adverbs adverb){
        System.out.println(this.getName()+move.getDes()+position.getDes()+adverb.getDes());
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
}
