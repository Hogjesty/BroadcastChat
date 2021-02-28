import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class User implements Runnable {
    public static List<DataOutputStream> outs;
    private DataInputStream in;

    public User(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        String s;
        while (true) {
            try {
                s = in.readUTF();
                System.out.println("New message: " + s);
                System.out.println("Broadcasting to other users");
                for(DataOutputStream out : outs) {
                    out.writeUTF(s);
                    out.flush();
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
}
