package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final String address = "127.0.0.1";
    private static String name;

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        name = args[1];
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("Hello, " + name + "!\nYou are connecting to " + address + ":" + port);
            Socket socket = new Socket(ipAddress, port);
            System.out.println("Connection established!");
            Thread input = new Thread(new Input(new DataInputStream(socket.getInputStream())));
            Thread output = new Thread(new Output(new DataOutputStream(socket.getOutputStream())));
            input.start();
            output.start();
        } catch (IOException e) {
            System.err.println("Unable to setup connection...");
            e.printStackTrace();
        }
    }

    private static class Input implements Runnable {
        private DataInputStream in;

        Input(DataInputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            String line;
            while (true) {
                try {
                    System.out.println("Enter Message: ");
                    line = in.readUTF();
                    System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Output implements Runnable {
        private DataOutputStream out;

        Output(DataOutputStream out) {
            this.out = out;
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;

            while(true) {
                try {
                    line = br.readLine();
                    out.writeUTF(name + ": " + line);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                br.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
