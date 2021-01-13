/**
 * Created by Zavodov on 27.07.2017.
 */
public class Thing implements IThing {

    private String name;
    private int count;
    private boolean visible;

    public Thing(String _name){
        name = _name;
        count = 1;
        visible = true;
    }

    public Thing(String _name, int _count){
        name = _name;
        count = _count;
        visible = true;
    }

    public Thing(String _name, int _count, boolean _visible){
        name = _name;
        count = _count;
        visible = _visible;
    }

    public String GetName(){
        return name == null ? "..." : name;
    }

    public int GetCount(){
        return count;
    }

    public boolean IsVisible() { return visible; }


}
