package converter;

import javafx.util.StringConverter;

public class CustomFloatStringConverter extends StringConverter<Float> {
    Float oldValue;
    public Float fromString(String value) {
        try {
            if (value == null) {
                return null;
            } else {
                value = value.trim();
                return value.length() < 1 ? oldValue : Float.parseFloat(value);
            } } catch (NumberFormatException e) {
            System.out.println("Wrong number");
            return oldValue;
        }
    }
    public String toString(Float value) {
        oldValue=value;
        return (value == null) ? "" : (String.format("%.3f",value));
    }
}
