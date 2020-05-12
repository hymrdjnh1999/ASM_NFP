package app.client;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private Socket socket;
    private ChatClient chatClient;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public WriteThread(Socket socket2, ChatClient chatClient2) {
        this.socket = socket2;
        this.chatClient = chatClient2;

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
            chatClient.setUserName(userName);
            dataOutputStream.writeUTF(userName);
            String mess;
            do {
                System.out.print(userName + " : ");
                mess = scanner.nextLine();
                dataOutputStream.writeUTF(mess);
            } while (!mess.equals("bye") || !mess.equals("quit"));

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
