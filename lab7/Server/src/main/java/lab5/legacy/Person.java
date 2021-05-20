package lab5.legacy;

import java.util.Calendar;
import java.util.Date;

public class Person implements Comparable<Person> {
    private Long id = generateID(); //Cant be null, have to be unique, automatically generated when instantiated
    private String name=""; //Cant be null or empty
    private Coordinates coordinates=null; //Cant be null
    private Date creationDate = Calendar.getInstance().getTime(); //Cant be null, utomatically generated when instantiated
    private Float height=0f; //Cant be null, larger than 0
    private Long weight=0l; //Cant be null, larger than 0
    private Color hairColor=null; //Cant be null
    private Country nationality=null; //Cant be null
    private Location location=null; //Cant be null
    private int owner_id=0;

    public Person(String name, Coordinates coordinates, Float height, Long weight, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.coordinates = coordinates;
        this.height = Math.round(height*1000f)/1000f;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }
    public Person(int owner_id, String name, Coordinates coordinates, Float height, Long weight, Color hairColor, Country nationality, Location location) {
        this.owner_id = owner_id;
        this.name = name;
        this.coordinates = coordinates;
        this.height = Math.round(height*1000f)/1000f;
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
        if (height.equals(null)) this.height = 0f;
        else this.height = Math.round(height*1000f)/1000f;
    }
    public void setWeight(Long weight){
        if (weight.equals(null)) this.weight = 0l;
        else this.weight = Math.round(weight*1000f)/1000l;
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
    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
    public int getOwner_id() {
        return owner_id;
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
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{id:"+getId()+",owner id:"+getOwner_id()+",name:"+getName()+";coordinates:{x:"+getCoordinates().getX()
                +";y:"+getCoordinates().getY()+"};creation_date:"+getCreationDate()+";height:"+getHeight()
                +";weight:"+getWeight()+";hair_color:"+getHairColor().toString().toLowerCase()+";nationality:"
                +getNationality().toString().toLowerCase().replace("_"," ") +";location:{x:" +getLocation().getX()+";y:"
                +getLocation().getY()+";z:"+getLocation().getZ()+";name:"+getLocation().getName()+"}}");
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
            for (Person p: Reader.collectionPerson) {
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








