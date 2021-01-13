/**
 * Created by Zavodov on 28.07.2017.
 */
public abstract class Creature implements ICreature {

    private String name;
    private int age;

    public Creature(){

    }

    public Creature (String _name){
        name = _name;

    }

    public Creature (String _name, int _age){
        name = _name;
        age = _age;
    }

    public String GetName(){
        return name;
    }

    public int GetAge() {
        return age;
    }

    public void SetAge(int _age) {
        age = _age;
    }

    public void HappyBirthday(){
        age++;
    }


}
