package commands;

import lab5.legacy.Person;

public class CommandUpdate extends Command {
    public CommandUpdate(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String s) {
        String[] d = s.split(",",2);
        Long cId;
        try {cId = Long.parseLong(d[0]);} catch (NumberFormatException ex) {
            return ("ID must be a number");
        }
        for (Person p: getCollection()) {
            //System.out.println(p.getId());
            if ((long)cId == (long)p.getId()) {
                getCollection().remove(p);
                p = new Person();
                p.setId(cId);
                if (!setData(p, d[1])) return "Parsing error";
                getCollection().add(p);
                return ("Updated person "+p.getName()+" successfully with id "+p.getId());
            }
        }
        return ("Person with input ID not found");
    }
}
