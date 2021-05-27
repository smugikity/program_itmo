package datapack;

import lab5.legacy.Person;

public class AddPack extends Pack {
    private Person person;
    public AddPack(Person person) {
        this.person = person;
    }
    public Person getPerson() {
        return person;
    }
}
