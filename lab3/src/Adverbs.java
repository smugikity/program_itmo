public enum Adverbs {
    CAREFULLY(" carefully"),
    GREENHAT(" with an old green hat"),
    EVENING(" in the evening of the summer solstice"),
    GIRL(" over her");
    final private String Des;
    Adverbs(String setDes) {
        this.Des = setDes;
    }
    public String getDes() {
        return Des;
    }
}
