public enum Positions {
    BAY(" to the bay"),
    COASTALREEDS(" in the coastal reeds"),
    MUD(" in the mud"),
    BOX(" to the box"),
    BANK(" on the bank");
    final private String Des;
    Positions(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
            return Des;
    }
}
