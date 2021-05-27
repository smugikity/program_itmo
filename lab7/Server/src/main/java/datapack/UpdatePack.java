package datapack;

import lab5.legacy.Person;

public class UpdatePack extends Pack {
    private long id;
    private Person person;
    public UpdatePack(long id, Person person) {
        this.id = id;
        this.person = person;
    }
    public Person getPerson() {
        return person;
    }
    public long getId() {
        return id;
    }
}
