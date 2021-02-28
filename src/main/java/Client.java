public class Client {

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
}
