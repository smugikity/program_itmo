import java.util.ArrayList;

/**
 * Created by Zavodov on 27.07.2017.
 */
public class Place {

    private String name;
    private Coordinates location;
    private ArrayList<Thing> obj = new ArrayList<Thing>();
    private ArrayList<Skill> skills = new ArrayList<Skill>();

    public Place(String _name){
        name = _name;
        location = null;
        System.out.println("Место - " + name + " (" + location.getName() + ") успешно создано");
    }

    public Place(String _name, Coordinates _location){
        name = _name;
        location = _location;
        System.out.println("Место - " + name + " (" + location.getName() + ") успешно создано");
    }

    public String getName() {
        return name == null ? "..." : name;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Thing> getObj() {
        return obj;
    }

    public Coordinates getLocation() {
        return location;
    }

    public boolean addObj(Thing _thing){
        if (obj.add(_thing)){
            String isVisible = "(видимое)";
            if (!_thing.IsVisible()) isVisible = "(не видимое)";

            System.out.println("В объект - \"" + name + "\" успешно добавлено " + _thing.GetName()
                    + " в количестве - " + _thing.GetCount() + isVisible);
            return true;
        }
        else{
            System.out.println("При добавлении объекта произошла ошибка...");
            return false;
        }
    }

    public void delObj(int ind){
        skills.remove(ind);
    }

    public void delObj(Thing _thing){
        skills.remove(_thing);
    }

    public  boolean addSkill(Skill _skill){
        return skills.add(_skill);
    }

}
