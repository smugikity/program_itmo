package com.lab5;
import java.util.*;

public class Person implements Comparable<Person> {
    private Long id = generateID(); //Cant be null, have to be unique, automatically generated when instantiated
    private String name=""; //Cant be null or empty
    private Coordinates coordinates=null; //Cant be null
    private Date creationDate = Calendar.getInstance().getTime(); //Cant be null, utomatically generated when instantiated
    private Float height=null; //Cant be null, larger than 0
    private Long weight=null; //Cant be null, larger than 0
    private Color hairColor=null; //Cant be null
    private Country nationality=null; //Cant be null
    private Location location=null; //Cant be null

    public Person(String name, Coordinates coordinates, Float height, Long weight, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }
    public Person() {
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setHeight(Float height) {
        this.height = height;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Color getHairColor() {
        return hairColor;
    }
    public Country getNationality() {
        return nationality;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public Float getHeight() {
        return height;
    }
    public Location getLocation() {
        return location;
    }
    public Long getId() {
        return id;
    }
    public Long getWeight() {
        return weight;
    }
    public String getName() {
        return name;
    }

    /**
     * Display data
     */
    public void display() {
        System.out.format("id: "+getId()+"\nname: "+getName()+"\ncoordinates:\n\tx: "+getCoordinates().getX()
                +"\n\ty: "+getCoordinates().getY()+"\ncreation date: "+getCreationDate()+"\nheight: "+getHeight()
                +"\nweight: "+getWeight()+"\nhair color: "+getHairColor().toString().toLowerCase()+"\nnationality: "
                +getNationality().toString().toLowerCase().replace("_"," ") +" \nlocation:\n\tx: " +getLocation().getX()+"\n\ty: "
                +getLocation().getY()+"\n\tz: "+getLocation().getZ()+"\n\tname: "+getLocation().getName()+"\n\n"); //+"\nvalue: "+getValue()+"\n"
    }

    /**
     * Generate a unique ID (8 digits)
     * @return id as long
     */
    private long generateID() {
        Boolean checked = false;
        Long idTemp = null;
        while (!checked) {
            checked = true;
            idTemp =Long.valueOf(Math.round(Math.random()*1000000000));
            for (Person p:Reader.collectionPerson) {
                if (id==p.getId() || idTemp < 100000000) checked=false;
            }
        }
        return idTemp;
    }

    /**
     * Value of location caculated by x^2 + y^2 + z^2
     * @return
     */
    public double getLocationValue() {
        return (Math.pow(getLocation().getX(),2)+Math.pow(getLocation().getY(),2)+Math.pow(getLocation().getZ(),2));
    }

    /**
     * Value of one instance calculated by sum of all summable variables (numeral variables)
     * @return
     */
    private Double getValue() {
        return getHeight()+getWeight()+getLocation().getX()+getLocation().getY()+getLocation().getZ()+getCoordinates().getX()+getCoordinates().getY();
    }

    /**
     * Compare based on getValue() method
     * @param o Person to compare with
     * @return 1 if larger, 0 if equal, -1 if smaller
     */
    @Override
    public int compareTo(Person o) {
        if (o == null || this.getValue()>o.getValue()) return 1;
        else if (this.equals(o)) return 0;
        else return -1;
    }
}








