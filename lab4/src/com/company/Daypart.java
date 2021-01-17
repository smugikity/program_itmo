package com.company;

public enum Daypart {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening"),
    NIGHT("Night");
    final private String Des;
    Daypart(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
        return Des;
    }
}
