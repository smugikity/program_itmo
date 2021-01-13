package com.company;

public enum Action {
    FLOAT(" floated"),
    CARRIED(" was carried"),
    LOCATED(" is located"),
    BOGGED(" was bogged down"),
    HOVER(" hovered"),
    CLUNG(" clunged"),
    BE(" were"),
    STAND(" stood"),
    SWAY(" was swaying in"),
    TWITCH(" twiched"),
    PULL(" pulled"),
    STARE(" stared"),
    BREAKTHROUGH(" broke through"),
    BROKEN(" was broken"),
    BLOWNAWAY(" was blown away"),
    RANINTO(" ran into");
    final private String Des;
    Action(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
        return Des;
    }
}
