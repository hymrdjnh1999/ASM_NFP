package app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import app.EncodeDeCode;

public class ChatServer {
    private Set<String> userNames = new HashSet<String>();
    private Set<Socket> listUser = new HashSet<Socket>();
    private DataOutputStream dataOutputStream;

    public class ServerRead extends Thread {
        Socket socket;

        public ServerRead(Socket socket) {
            this.socket = socket;

        }

        @Override
        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                printUser();
                String userName = dataInputStream.readUTF();
                String decode = EncodeDeCode.decode(userName);
                String decodeUserName = decode;
                userNames.add(decode);
                String reportConnect = decode + " connected to server";
                System.out.println(reportConnect);
                String encode = EncodeDeCode.encode(reportConnect);
                sendMessageToAllClient(encode, socket);
                // System.out.println(reportConnect);
                while (true) {
                    String read = dataInputStream.readUTF();
                    decode = EncodeDeCode.decode(read);
                    encode = EncodeDeCode.encode(decodeUserName + " : " + decode);
                    sendMessageToAllClient(encode, socket);
                    if (decode.equals("bye")) {
                        removeUser(userName, socket);
                        System.out.println(decodeUserName + " has quitted");
                        socket.close();
                        encode = EncodeDeCode.encode(decodeUserName + " has quitted");
                        sendMessageToAllClient(encode, socket);
                        break;
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }

    void printUser() {
        if (!(userNames.isEmpty())) {
            try {
                String encode = EncodeDeCode.encode("User connected " + getUserName().toString());
                dataOutputStream.writeUTF(encode);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        } else {
            try {
                String encode = EncodeDeCode.encode("No other user connected");
                dataOutputStream.writeUTF(encode);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void execute() {

        System.out.print("Enter port : ");
        int port = Integer.parseInt(new Scanner(System.in).nextLine());
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("waiting client connection on port : " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ServerRead serverRead = new ServerRead(socket);
                listUser.add(socket);
                serverRead.start();

            }

        } catch (Exception e) {
            System.out.println("Error in the server " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().execute();
    }

    void sendMessageToAllClient(String message, Socket excludeUser) {
        for (Socket user : this.listUser) {
            if (!user.equals(excludeUser)) {
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(user.getOutputStream());
                    dataOutputStream.writeUTF(message);
                } catch (Exception e) {

                }
            }
        }
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void removeUser(String userName, Socket user) {
        boolean removeUserName = this.userNames.remove(userName);
        if (removeUserName) {
            System.out.println("The user " + userName + " disconnect!");
        }
    }

    /**
     * @return the userName
     */
    public Set<String> getUserName() {
        return this.userNames;
    }

    // boolean hasUser() {
    // return this.userThreads.isEmpty();
    // }
}