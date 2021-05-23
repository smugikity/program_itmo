package converter;

import javafx.util.StringConverter;

public class CustomLongStringConverter extends StringConverter<Long> {
    Long oldValue;
    public Long fromString(String value) {
        try {
            if (value == null) {
                return null;
            } else {
                value = value.trim();
                return value.length() < 1 ? oldValue : Integer.parseInt(value);
            } } catch (NumberFormatException e) {
            System.out.println("Wrong number");
            return oldValue;
        }
    }
    public String toString(Long value) {
        oldValue=value;
        return (value == null) ? "" : (Float.toString(value));
    }
}
