package app.client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import app.Encode;

public class ClientWrite extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);
    private String userName;

    public ClientWrite(Socket socket2, String userName) {
        this.socket = socket2;
        this.userName = userName;
        try {
            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (Exception e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

    }

    protected void execute() {
        try {

            String mess;
            String encode;
            do {
                mess = scanner.nextLine();

                encode = Encode.encode(mess);
                dataOutputStream.writeUTF(encode);
            } while (!mess.equals("bye"));
            ChatClient.mainMenu();
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        execute();
    }
}
