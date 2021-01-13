import java.util.ArrayList;

/**
 * Created by Zavodov on 27.07.2017.
 */

public class Human extends Creature {

    private String surName;

    private ArrayList<Skill> skills = new ArrayList<Skill>();

    public Human(){
        System.out.println("Безликий человек успешно создано");
    }

    public Human (String _name, String _surName){
        super (_name);
        surName = _surName;
        System.out.println("Человек - " + _name + " успешно создано");
    }

    public Human (String _name, String _surName, ArrayList<Skill> _skills){
        super (_name);
        surName = _surName;
        skills = _skills;
        System.out.println("Человек - " + _name + " " + surName + " успешно создано");
    }

    public String getSurName() {
        return surName;
    }

    public Human (String _name, String _surName, int _age){
        super (_name, _age);
        surName = _surName;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void delSkill(Skill skill){ skills.remove(skill);}

    public boolean addSkill(Skill skill){
        if (skills.add(skill)){
            System.out.println("Объекту - \"" + this.GetName() + "\" успешно присвоено умение " + skill.getName()
                    + " (" + skill.getInfo()+ ")");
            return true;
        }
        else{
            System.out.println("При добавлении умения произошла ошибка...");
            return false;
        }
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
