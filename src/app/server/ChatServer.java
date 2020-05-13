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
    static Scanner scanner = new Scanner(System.in);

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

    static int isNumeric() {
        int number = -1;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());

                break;
            } catch (Exception e) {

                System.out.print("Please enter decimal integer : ");

            }
        }
        return number;
    }

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

    public static void clrscr() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public void execute() {
        System.out.println("Server is running!");
        System.out.print("Enter connection port : ");
        int port = isNumeric();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            clrscr();
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