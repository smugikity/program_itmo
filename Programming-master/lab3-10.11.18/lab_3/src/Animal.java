import org.omg.CORBA.StringHolder;

/**
 * Created by Zavodov on 31.07.2017.
 */
public class Animal extends Creature {

    private AnimalType type;

    public Animal(){

    }

    public Animal(String _name, int _age){
        super(_name, _age);
        System.out.println("Животное - " + _name + " успешно создано");
    }

    public Animal(String _name, int _age, AnimalType _type){
        super(_name, _age);
        type = _type;
        System.out.println("Животное - " + _name + " успешно создано");
    }

    public AnimalType GetType(){
        return type;
    }

    public void SetType(AnimalType _type){
        type = _type;
        String typeName = "";
        switch (_type){
            case Bear : typeName = "Медведь";
                break;
            case Wolf : typeName = "Волк";
                break;
            case Tiger : typeName = "Тигр";
                break;
            case Fox : typeName = "Лисица";
                break;
            case Pig : typeName = "Свинья";
                break;
        }
        System.out.println(GetName() + " причислен к виду " + typeName);
    }

    @Override
    public String toString() {
        return GetName() + " ("+type+") - " + GetAge();
    }

    @Override
    public boolean equals(Object obj) {
        return (this.hashCode() == obj.hashCode());
    }

    @Override
    public int hashCode() {
        return GetAge() + GetName().hashCode() + type.hashCode();
    }
}
