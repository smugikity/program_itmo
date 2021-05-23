package converter;

import javafx.util.StringConverter;

public class CustomDoubleStringConverter extends StringConverter<Double> {
    Double oldValue;
    public Double fromString(String value) {
        try {
            if (value == null) {
                return null;
            } else {
                value = value.trim();
                return value.length() < 1 ? oldValue : Double.parseDouble(value);
            } } catch (NumberFormatException e) {
            System.out.println("Wrong number");
            return oldValue;
        }
    }
    public String toString(Double value) {
        oldValue=value;
        return (value == null) ? "" : (String.format("%.3f",value));
    }
}