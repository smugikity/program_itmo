package datapack;

import lab5.legacy.Person;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class InitialMegaPack implements Serializable {
    private HashSet<Person> people;
    public InitialMegaPack(Set<Person> people) {
        this.people = (HashSet<Person>) people;
    }
    public HashSet<Person> getPeople() {
        return people;
    }
}
