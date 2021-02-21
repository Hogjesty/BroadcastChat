import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String ... args) {
        User.outs = new ArrayList<>();
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
            User.outs.add(new DataOutputStream(s1.getOutputStream()));
            User.outs.add(new DataOutputStream(s2.getOutputStream()));

            Thread t1 = new Thread(new User(new DataInputStream(s1.getInputStream())));
            Thread t2 = new Thread(new User(new DataInputStream(s1.getInputStream())));
            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
