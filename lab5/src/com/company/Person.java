package com.company;
import java.util.*;

public class Person implements Comparable<Person> {
    private Long id = generateID();; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name=""; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates=null; //Поле не может быть null
    private Date creationDate = Calendar.getInstance().getTime(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height=null; //Поле не может быть null, Значение поля должно быть больше 0
    private Long weight=null; //Поле не может быть null, Значение поля должно быть больше 0
    private Color hairColor=null; //Поле не может быть null
    private Country nationality=null; //Поле не может быть null
    private Location location=null; //Поле не может быть null

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
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        System.out.format("id: %9d"+"\nname: "+getName()+"\ncoordinates:\n\tx: "+getCoordinates().getX()
                +"\n\ty: "+getCoordinates().getY()+"\ncreation date: "+getCreationDate()+"\nheight: "+getHeight()
                +"\nweight: "+getWeight()+"\nhair color: "+getHairColor().toString().toLowerCase()+"\nnationality: "
                +getNationality().toString().toLowerCase().replace("_"," ") +" \nlocation:\n\tx: " +getLocation().getX()+"\n\ty: "
                +getLocation().getY()+"\n\tz: "+getLocation().getZ()+"\n\tname: "+getLocation().getName()+"\nvalue: "+getValue()+"\n",getId());
    }

    /**
     * Generate a unique ID (8 digits)
     * @return
     */
    private long generateID() {
        Boolean checked = false;
        Long idTemp = null;
        while (!checked) {
            checked = true;
            idTemp =Long.valueOf(Math.round(Math.random()*1000000000));
            for (Person p:Reader.collectionPerson) {
                if (id==p.getId()) checked=false;
            }
        }
        return idTemp;
    }

    /**
     * Value of one instance caculated by sum of all summable variables (numeral variables)
     * @return
     */
    private Double getValue() {
        return getHeight()+getWeight()+getLocation().getX()+getLocation().getY()+getLocation().getZ()+getCoordinates().getX()+getCoordinates().getY();
    }

    @Override
    public int compareTo(Person o) {
        if (o == null || this.getValue()>o.getValue()) return 1;
        else if (this.equals(o)) return 0;
        else return -1;
    }
}








