package com.company;

public enum Position {
    BAY(" to the bay"),
    HOUSE(" the house"),
    COASTALREEDS(" the coastal reeds"),
    MUD(" in the mud"),
    BOX(" to the box"),
    BANK(" on the bank"),
    OVERHER(" over her"),
    NONAVAILABLE("");
    final private String Des;
    Position(String setDes) {
            this.Des = setDes;
        }
    public String getDes() {
            return Des;
        }
}
