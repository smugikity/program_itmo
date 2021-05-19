package lab5.legacy;

public class Location {
    private Double x; //Cant be null
    private Long y; //Cant be null
    private Double z; //Cant be null
    private String name; //Cant be null
    public Location(Double x, Long y, Double z, String name) {
        this.x = Math.round(x*1000d)/1000d;
        this.y = y;
        this.z = Math.round(z*1000d)/1000d;
        this.name = name;
    }
    public Location() {}
    public void setX (Double x) {
        this.x = Math.round(x*1000d)/1000d;
    }
    public void setY (Long y) {
        this.y = y;
    }
    public void setZ (Double z) {
        this.z = Math.round(z*1000d)/1000d;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Double getX() {
        return x;
    }
    public Double getZ() {
        return z;
    }
    public Long getY() {
        return y;
    }
}