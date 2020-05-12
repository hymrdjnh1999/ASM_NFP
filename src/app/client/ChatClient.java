package app.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {

        execute();
    }

    private static String hostName = "", userName;
    private static int port = -1;
    static Scanner scanner = new Scanner(System.in);
    private static Socket socket;

    public ChatClient(Socket socket) {
        this.socket = socket;

    }

    public static void mainMenu() {
        while (true) {
            System.out.println("=============================");
            System.out.println("Sig up to chat");
            System.out.println("=============================");
            System.out.println("1.Regist");
            System.out.println("2.Join to chat room");
            System.out.println("0.Exit ");
            System.out.println("=============================");
            System.out.print("#Select : ");
            int select = Integer.parseInt(scanner.nextLine());
            if (select == 0) {
                System.exit(1);
            }
            switch (select) {
                case 1:
                    System.out.print("Enter host name : ");
                    hostName = scanner.nextLine();
                    System.out.print("Enter port server : ");
                    port = Integer.parseInt(scanner.nextLine());
                    break;
                case 2:
                    break;
            }
            if (select == 2)
                break;

        }

        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connect to the chat server ");
            new ReadThread(socket).start();
            new WriteThread(socket).start();

        } catch (UnknownHostException e) {
            System.out.println("Server not found : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    public static void execute() {
        mainMenu();
    }

    /**
     * @param userName the userName to set
     */
    public static void setUserName(String _Name) {
        userName = _Name;
    }

    /**
     * @return the userName
     */
    public static String getUserName() {
        return userName;
    }

}