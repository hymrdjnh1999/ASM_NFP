package app.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.Encode;

/**
 * ServerHandle
 */
public class ServerHandle {
    public static List<String> userNames = new ArrayList<String>();
    public static List<String> userNameNotEncode = new ArrayList<>();
    protected static List<Socket> listUser = new ArrayList<Socket>();
    protected static List<Socket> listUserNotEncode = new ArrayList<>();
    static DataOutputStream dataOutputStream;
    static Scanner scanner = new Scanner(System.in);
    protected static List<String> chatLog = new ArrayList<String>();
    protected static List<String> chatLogNotEncode = new ArrayList<>();

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

    static void sendMessageToAllClientNotEncode(String mess, Socket CurrentSocket) {
        for (Socket user : listUserNotEncode) {
            if (!user.equals(CurrentSocket)) {
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(user.getOutputStream());
                    dataOutputStream.writeUTF(mess);
                } catch (Exception e) {
                    // TODO: handle exception
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

    static void handle() {
        System.out.println("Server is running!");
        System.out.print("Enter connection port : ");
        int port = isNumeric();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ChatServer.clrscr();
            System.out.println("waiting client connection on port : " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ServerRead serverRead = new ServerRead(socket);
                serverRead.start();

            }

        } catch (Exception e) {
            System.out.println("Error in the server " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void printUser() {
        if (!(userNames.isEmpty())) {
            try {
                String encode = Encode.encode("User connected " + userNames.toString());
                dataOutputStream.writeUTF(encode);
            } catch (

            IOException e) {
            }
        } else {
            try {
                String encode = Encode.encode("No other user connected");
                dataOutputStream.writeUTF(encode);
            } catch (Exception e) {
            }
        }
    }

    static void printUserNotEncode() {
        if (!(userNameNotEncode.isEmpty())) {
            try {
                dataOutputStream.writeUTF("User connected " + userNameNotEncode.toString());
            } catch (

            IOException e) {
            }
        } else {
            try {

                dataOutputStream.writeUTF("No other user connected");
            } catch (Exception e) {
            }
        }
    }
}