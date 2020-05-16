package app.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import app.server.ChatServer;

public class handleInput {
    static Scanner scanner = new Scanner(System.in);
    protected static String hostName;
    protected static int port;

    public static int isNumeric() {
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

    static String checkStringIsEmpty() {
        String name = "";
        do {
            name = scanner.nextLine();

        } while (name == null || name.trim().isEmpty());

        return name;
    }

    public static Socket checkSocketIsCorrect() {
        Socket s = null;

        while (true) {
            System.out.print("Enter host name : ");
            hostName = checkStringIsEmpty();
            System.out.print("Enter port server : ");
            while (true) {
                try {
                    port = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.err.print("Please input integer decimal : ");
                }
            }
            try {

                if (new Socket(hostName, port).isConnected())
                    break;

            } catch (Exception e) {
                ChatServer.clrscr();

                System.err.println("Wrong host name or not found server!\nPlease re-enter port and your host name !");
            }
        }
        System.out.println("Connection successfully with your host name : " + hostName + " port server : " + port);
        System.out.print("Enter to continue... ");
        scanner.nextLine();
        try {
            s = new Socket(hostName, port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;

    }

}