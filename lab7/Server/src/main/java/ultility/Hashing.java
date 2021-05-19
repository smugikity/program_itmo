package ultility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Hashing {
    public static String hashSHA384(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Hash algorimth not found");
            System.exit(0);
            return null;
        }
    }

    public static void main (String [] args) {
        Scanner scanner = new Scanner(System.in);
        String rs="";
        char c;
        while ((c = (char)scanner.nextByte())!='\n') {
            rs+=c;
            System.out.println(c);
        }
        System.out.println(rs);
    }
}
