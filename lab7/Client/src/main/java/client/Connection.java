package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    public static BufferedReader input;
    public static PrintWriter output;
    public static Connection connection;
    public static Connection getInstance() {return connection;}

    //localhost, 6967
    public Connection(String host,int port) {
        try {
            connection = this;
            Socket socket = new Socket(InetAddress.getLocalHost(),port);
            socket.setSoTimeout(5000);
            //output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("allo"+read());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String read() {
        while (true) {
            try {
                StringBuilder result = new StringBuilder(); String line = ""; int c;
                while((c=input.read())!='\0') {
                    result.append((char) c);
                }
                return result.toString();
            } catch (IOException | RuntimeException e) {
                //Dialog.throwException(e);
                continue;
            }
        }
    }

    public void write(String str) {
        output.println(str);
    }

    private char[] toChars(String str) {
        char[] ch = new char[str.length()];
        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        return ch;
    }


}
