package converter;

import javafx.util.StringConverter;
import lab5.legacy.Color;

public class CustomColorStringConverter extends StringConverter<Color> {
    Color oldValue;
    public Color fromString(String value) {
        try {
            if (value == null) {
                return null;
            } else {
                value = value.trim();
                return value.length() < 1 ? oldValue : Color.valueOf(value.toUpperCase());
            } } catch (IllegalArgumentException e) {
            System.out.println("Wrong color");
            return oldValue;
        }
    }
    public String toString(Color value) {
        oldValue=value;
        return (value == null) ? "" : (value.toString());
    }
}


