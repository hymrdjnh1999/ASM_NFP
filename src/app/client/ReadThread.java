package app.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread extends Thread {
    private Socket socket;
    private ChatClient chatClient;
    DataInputStream dataInputStream;

    public ReadThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.chatClient = chatClient;
        try {
            // InputStream inputStream =;
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = dataInputStream.readUTF();
                if (response.isEmpty()) {
                    return;
                }
                System.out.println("\n" + response);
                if (chatClient.getUserName() != null) {
                    System.out.print(chatClient.getUserName() + " : ");
                }
            } catch (Exception e) {
                System.out.println("Bye bye");
                System.exit(1);
                break;
            }
        }
    }
}
