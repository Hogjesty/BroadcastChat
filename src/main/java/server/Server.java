package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String ... args) {
        final int port1 = Integer.parseInt(args[0]);
        final int port2 = Integer.parseInt(args[1]);
        try {
            ServerSocket ss1 = new ServerSocket(port1);
            System.out.println("Waiting for client 1...");
            Socket s1 = ss1.accept();

            ServerSocket ss2 = new ServerSocket(port2);
            System.out.println("Waiting for client 2...");
            Socket s2 = ss2.accept();

            System.out.println("Got Two Clients. Chat is started.");

            Thread t1 = new Thread(new User(s1.getInputStream(), s1.getOutputStream()));
            Thread t2 = new Thread(new User(s2.getInputStream(), s2.getOutputStream()));
            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
