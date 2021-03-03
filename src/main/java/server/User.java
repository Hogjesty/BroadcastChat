package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User implements Runnable {
    private static final List<User> users = new ArrayList<>();

    private final DataInputStream in;
    private final DataOutputStream out;

    public User(InputStream in, OutputStream out) {
        this.in = new DataInputStream(in);
        this.out = new DataOutputStream(out);
        users.add(this);
    }

    @Override
    public void run() {
        String s;
        while (true) {
            try {
                s = in.readUTF();
                System.out.println("New message: " + s);
                System.out.println("Broadcasting to other users");
                for(User user : users) {
                    user.send(s);
                }
                System.out.println("Done. Waiting for new messages...");
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void send(String s) throws IOException {
        this.out.writeUTF(s);
        this.out.flush();
    }
}
