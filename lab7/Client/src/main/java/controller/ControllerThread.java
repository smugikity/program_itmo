package controller;

public class ControllerThread implements Runnable {
    private MainController main;
    private int commit;
    public ControllerThread(MainController main) {
        this.main = main;
    }
    @Override
    public void run() {
        while (true) {

        }
    }
}
