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

    public ChatClient(Socket socket) {
        this.socket = socket;

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
            int select = handleInput.isNumeric();
            if (select == 0) {
                System.out.println("Bye byeeeee");
                System.exit(1);
            }
            switch (select) {
                case 1:
                    ChatServer.clrscr();
                    socket = handleInput.checkSocketIsCorrect();
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
                port = handleInput.port;
                hostName = handleInput.hostName;
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