package app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UserThread extends Thread {
    private ChatServer chatServer;
    private Socket socket;

    public UserThread(Socket socket, ChatServer chatServer) {
        this.chatServer = chatServer;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            printUser(dataOutputStream);

            String userName = dataInputStream.readUTF();
            chatServer.addUserName(userName);
            String serverMessage = "New user connected: " + userName;

            chatServer.sendMessageToAllClient(serverMessage, this);
            String clientMessage;
            do {
                clientMessage = dataInputStream.readUTF();

                serverMessage = userName + " : " + clientMessage;

                chatServer.sendMessageToAllClient(serverMessage, this);

            } while (clientMessage.equals("bye"));
            chatServer.removeUser(userName, this);
            socket.close();
            serverMessage = userName + " has quitted";
            chatServer.sendMessageToAllClient(serverMessage, this);

        } catch (IOException e) {
            System.err.println("Error in UserThread " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void printUser(DataOutputStream outputStream) {
        if (!(chatServer.getUserName() == null)) {
            try {
                outputStream.writeUTF("Connected user : " + chatServer.getUserName());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else
            try {
                outputStream.writeUTF("No other user connected!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    }

    public void sendMessage(String message, DataOutputStream outputStream) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}