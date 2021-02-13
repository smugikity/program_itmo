package com.company;
import java.util.*;

public class Person {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height; //Поле не может быть null, Значение поля должно быть больше 0
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле не может быть null
    public Person(Long id, String name, Coordinates coordinates, Date creationDate, Float height, Long weight, Color hairColor, Country nationality, Location location) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }
    public Person() {}
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

    public void lemmeseeurbooty() {
        System.out.println("id: "+getId()+"" + "\nname: "+getName()+"\ncoordinates:\n\tx: "+getCoordinates().getX()
                +"\n\ty: "+getCoordinates().getY()+"\ncreation date: "+getCreationDate()+"\nheight: "+getHeight()
                +"\nweight: "+getWeight()+"\nhair color: "+getHairColor().toString().toLowerCase()+"\nnationality: "
                +getNationality().toString().toLowerCase().replace("_"," ") +" \nlocation:\n\tx: " +getLocation().getX()+"\n\ty: "
                +getLocation().getY()+"\n\tz: "+getLocation().getZ()+"\n\tname: "+getLocation().getName()+"\n");
    }

}








