package app.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import app.Encode;

public class ChatServer {
    public static Set<String> userNames = new HashSet<String>();
    private static Set<Socket> listUser = new HashSet<Socket>();
    static DataOutputStream dataOutputStream;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        execute();
    }

    static void sendMessageToAllClient(String message, Socket excludeUser) {
        for (Socket user : listUser) {
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

    static void execute() {
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

    static void removeUser(String userName, Socket user) {
        boolean removeUserName = userNames.remove(userName);
        if (removeUserName) {
            System.out.println("The user " + userName + " disconnect!");
        }
    }

    /**
     * @return the userName
     */
    static Set<String> getUserName() {
        return userNames;
    }

    static void printUser() {
        if (!(userNames.isEmpty())) {
            try {
                String encode = Encode.encode("User connected " +

                        ChatServer.getUserName().toString());
                ChatServer.dataOutputStream.writeUTF(encode);
            } catch (

            IOException e) {
                // TODO Auto-generated catch block
            }
        } else {
            try {
                String encode = Encode.encode("No other user connected");
                dataOutputStream.writeUTF(encode);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    // boolean hasUser() {
    // return this.userThreads.isEmpty();
    // }
}