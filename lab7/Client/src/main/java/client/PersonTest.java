package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonTest {
    private String name;
    private String height;
    private String weightcc;
    public PersonTest(String name, String height, String weight) {
        this.name= name;
        this.height= height;
        this.weightcc= weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weightcc = weight;
    }

    public String getName() {
        return name;
    }

    public StringProperty weightProperty() {
        return new SimpleStringProperty(this,"weight",getWeight());
    }

    public String getWeight() {
        return weightcc;
    }

    public String getHeight() {
        return height;
    }
}

