/**
 * Created by Zavodov on 27.07.2017.
 */
public class Coordinates {

    private String name;
    private Double x;
    private Double y;

    public Coordinates (double _x, double _y){
        x = _x;
        y = _y;
        name = x.toString() + "/" + y.toString();
    }

    public Coordinates (double _x, double _y, String _name){
        x = _x;
        y = _y;
        name = _name;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public double[] getCoordinates(){
        double[] coord = {x, y};
        return  coord;
    }
}
