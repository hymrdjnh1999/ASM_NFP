package app.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import app.server.ChatServer;

public class ChatClient {

    private static String hostName = "";
    private static int port = -1;
    static Scanner scanner = new Scanner(System.in);
    static Socket socket;

    public static void main(String[] args) throws IOException {

        mainMenu();
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

    public ChatClient(Socket socket) {
        this.socket = socket;

    }

    static void checkSocketIsCorrect() {
        while (true) {
            System.out.print("Enter host name : ");
            hostName = scanner.nextLine();
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
                else
                    socket.close();
            } catch (Exception e) {
                ChatServer.clrscr();

                System.err.println("Wrong host name or not found server!\nPlease re-enter port and your host name !");
            }
        }
        System.out.println("Connection successfully with your host name : " + hostName + " port server : " + port);
        System.out.print("Enter to continue... ");
        scanner.nextLine();

    }

    protected static void selectChatRoomMenu() {
        String select = "";
        while (true) {
            ChatServer.clrscr();
            System.out.println("==============================");
            System.out.println("|      Choose chat room      |");
            System.out.println("==============================");
            System.out.println("| 1.Not have encode          |");
            System.out.println("| 2.Have encode              |");
            System.out.println("==============================");
            System.out.print("#Select : ");
            select = scanner.nextLine();
            if (select.equals("1") || select.equals("2")) {
                break;
            } else {
                System.out.print("Not have this option!\nEnter to continue...");
                scanner.nextLine();
            }
        }

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            if (select.equals("1")) {

                dataOutputStream.writeUTF(select);
                new readNotDecode(socket).start();
                new writeNotEncode(socket).start();

            } else {
                dataOutputStream.writeUTF(select);
                new ClientRead(socket).start();
                new ClientWrite(socket).start();

            }
        } catch (Exception e) {
        }
    }

    protected static void mainMenu() throws IOException {
        while (true) {
            ChatServer.clrscr();
            System.out.println("=============================");
            System.out.println("|      Sig up to chat       |");
            System.out.println("=============================");
            System.out.println("| 1.Regist                  |");
            System.out.println("| 2.Join to chat room       |");
            System.out.println("| 0.Exit                    |");
            System.out.println("=============================");
            System.out.print("#Select : ");
            int select = isNumeric();
            if (select == 0) {
                System.out.println("Bye byeeeee");
                System.exit(1);
            }
            switch (select) {
                case 1:
                    ChatServer.clrscr();
                    checkSocketIsCorrect();
                    break;
                case 2:
                    ChatServer.clrscr();

                    break;

                default:
                    System.err.print("Not have this option!\nEnter to continue...");
                    scanner.nextLine();
                    break;

            }
            if (select == 2) {

                if (port != -1 && !(hostName.equals(""))) {

                    socket = new Socket(hostName, port);
                    break;
                } else {

                    System.out.print("Please regist before join to room!\nEnter to continue...");
                    scanner.nextLine();
                }

            }

        }
        selectChatRoomMenu();
    }

    /**
     * @param userName the userName to set
     */

    /**
     * @return the userName
     */

}