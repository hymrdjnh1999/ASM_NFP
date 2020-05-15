package app.client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class writeNotEncode extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);
    private String userName;

    public writeNotEncode(Socket socket2, String userName) {
        this.socket = socket2;
        this.userName = userName;
        try {
            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (Exception e) {
        }

    }

    @Override
    public void run() {
        try {

            String mess;
            do {
                mess = scanner.nextLine();
                dataOutputStream.writeUTF(mess);
            } while (!mess.equals("bye"));
            ChatClient.mainMenu();
        } catch (Exception e) {
        }
    }
}