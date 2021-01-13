public enum Moves {
    FLOAT(" floated"),
    CARRIED(" was carried"),
    LOCATED(" is located"),
    BOGGED(" was bogged down"),
    HOVER(" hovered"),
    CLUNG(" clunged"),
    BE(" were"),
    STAND(" stood"),
    SWAY(" was swaying"),
    TWITCH(" twiched"),
    PULL(" pulled"),
    STARE(" stared");
    final private String Des;
    Moves(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
        return Des;
    }
}
