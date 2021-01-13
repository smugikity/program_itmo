import java.util.ArrayList;

/**
 * Created by Zavodov on 20.07.2017.
 */
public class MainGo {

    public static void main(String[] args) {

        Animal bearPooh = new Animal("Винни", 100500);
        bearPooh.SetType(AnimalType.Bear);

        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(new Skill("Ходьба", null));
        skills.add(new Skill("Размышление", "Размышления о Том и о Сем"));

        Human kristopher = new Human("Кристофер","Робин", skills);
        Human secondHuman = new Human();
              secondHuman.setSkills(skills);

        Place hill = new Place("Вершина холма", new Coordinates(9.75,9.75));
              hill.addObj(new Thing("Капитанский мостик"));

        kristopher.addSkill( new Skill("Считать", "Считать все, кроме деревьев"));

        hill.addObj(new Thing("Дерево", 63));
        hill.addObj(new Thing("Дерево", 1, false));

    }




}
