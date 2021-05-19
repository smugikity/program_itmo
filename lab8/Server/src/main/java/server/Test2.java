package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test2 {
    public static void main(final String[] args) throws IOException {
        char passwordArray[] = System.console().readPassword("Enter password: ");
        System.out.println(passwordArray);
        String password = PasswordField.readPassword("Enter password:  ");
        System.out.println("Password entered was:" + password + password.isEmpty()+password.length());
    }
}


class PasswordField {

    public static String readPassword (String prompt) throws IOException {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        password = in.readLine();
        return password;
        //et.stopMasking();
        //return password;
    }
}

class EraserThread implements Runnable {
    private boolean stop;

    public EraserThread(String prompt) {
        System.out.print(prompt);
    }

    public void run () {
        while (!stop){
            System.out.print("\010*");
            try {
                Thread.currentThread().sleep(1);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void stopMasking() {
        this.stop = true;
    }
}