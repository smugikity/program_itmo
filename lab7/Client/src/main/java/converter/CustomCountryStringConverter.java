package converter;

import javafx.util.StringConverter;
import lab5.legacy.Country;

public class CustomCountryStringConverter extends StringConverter<Country> {
    Country oldValue;
    public Country fromString(String value) {
        try {
            if (value == null) {
                return null;
            } else {
                value = value.trim();
                return value.length() < 1 ? oldValue : Country.valueOf(value.toUpperCase());
            } } catch (IllegalArgumentException e) {
            System.out.println("Wrong country");
            return oldValue;
        }
    }
    public String toString(Country value) {
        oldValue=value;
        return (value == null) ? "" : (value.toString());
    }
}





