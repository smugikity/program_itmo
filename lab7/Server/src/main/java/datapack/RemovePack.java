package datapack;

import java.util.HashSet;
import java.util.Set;

public class RemovePack extends Pack {
    private HashSet<Long> id;
    public RemovePack(Set<Long> id) {
        this.id = (HashSet<Long>) id;
    }
    public RemovePack(HashSet<Long> id) {
        this.id = id;
    }
    public HashSet<Long> getId() {
        return id;
    }
}
