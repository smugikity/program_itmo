package server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main (String [] args) {
        String user="s308549";
        String host="se.ifmo.ru";
        int port=2222;
        String password="edp036";
        int tunnelLocalPort=8594;
        int tunnelRemotePort=5432;
        String tunnelRemoteHost="pg";
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            localUserInfo lui = new localUserInfo(password);
            session.setUserInfo(lui);
            //CONNECT SSH
            System.out.println("Устанавливается ssh подключение к " + host + " : " + port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
            //DO PORT FORWARDING
            int assingedPort = session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            System.out.println("Подключение установлено.");
            System.out.println("localhost:" + assingedPort + " -> " + tunnelRemoteHost + ":" + tunnelRemotePort);

        } catch (JSchException e) {
            System.out.println("Ошибка подключения через ssh-туннель.");
            e.printStackTrace();
        }

        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:8594/studs",user,password);){
            Class.forName("org.postgresql.Driver");
            System.out.println("Opened database successfully");
            Scanner sc = new Scanner(System.in);
//            String sql = "CREATE TABLE COMPANY " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " AGE            INT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " SALARY         REAL)";
            while (true) {
                try {
                    ResultSet resultSet = c.createStatement().executeQuery(sc.nextLine());
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (resultSet.next()) {
                        for (int n=1;n<6;n++)
                        System.out.print(resultSet.getString(n)+" ");
                        System.out.println();
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage()+" / Code: "+e.getErrorCode());
                    continue;
                }
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    static class localUserInfo implements UserInfo {
        String passwd;
        public localUserInfo(String passwd) { this.passwd = passwd; }
        public String getPassword() { return passwd; }
        public boolean promptYesNo(String str) { return true; }
        public String getPassphrase() { return null; }
        public boolean promptPassphrase(String message) { return true; }
        public boolean promptPassword(String message) { return true; }
        public void showMessage(String message) { }
    }
}
