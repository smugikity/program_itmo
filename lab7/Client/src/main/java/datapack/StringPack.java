package datapack;

public class StringPack extends Pack {
    private boolean isSuccess;
    private String toPrint;
    public StringPack(boolean isSuccess, String toPrint) {
        this.isSuccess = isSuccess;
        this.toPrint = toPrint;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public String toPrint() {
        return toPrint;
    }
}
