package app.client;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import app.EncodeDeCode;

public class WriteThread extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public WriteThread(Socket socket2) {
        this.socket = socket2;

        try {

            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (Exception e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            Console console = System.console();
            String userName = console.readLine("\nEnter yor name : ");
            ChatClient.setUserName(userName);
            String encode = EncodeDeCode.encode(userName);
            dataOutputStream.writeUTF(encode);
            String mess;
            do {
                System.out.print(userName + " : ");
                mess = scanner.nextLine();
                encode = EncodeDeCode.encode(mess);
                dataOutputStream.writeUTF(encode);
            } while (!mess.equals("bye") || !mess.equals("quit"));

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
