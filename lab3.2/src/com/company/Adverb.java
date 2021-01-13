package com.company;

public enum Adverb {
    CAREFULLY(" carefully"),
    GREENHAT(" with an old green hat"),
    EVENING(" in the evening of the summer solstice"),
    GIRL(" over her"),
    GENTLY(" gently"),
    STRONGLY(" strongly"),
    ONCEORTWICE(" once or twice");
    final private String Des;
    Adverb(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
        return Des;
    }
}
