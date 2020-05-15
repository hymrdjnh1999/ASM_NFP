package app.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import app.Encode;
import app.server.ChatServer;

public class MonitorClient {
    static Socket socket;
    static String hostName = "";
    static int port = -1;
    static Scanner scanner = new Scanner(System.in);

    static void checkSocketIsCorrect() throws UnknownHostException, IOException {

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
                Socket socketTest = new Socket(hostName, port);

                if (socketTest.isConnected()) {
                    break;
                } else
                    socketTest.close();
            } catch (Exception e) {
                ChatServer.clrscr();

                System.err.println("Wrong host name or not found server!\nPlease re-enter port and your host name !");
            }
        }
        socket = new Socket(hostName, port);
        System.out.println("Connection successfully with your host name : " + hostName + " port server : " + port);
        System.out.print("Enter to continue... ");
        scanner.nextLine();

    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        checkSocketIsCorrect();
        String select;
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
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(select);
        if (select.equals("1")) {
            dataOutputStream.writeUTF("tao la sep2#e2dddr44faDKRd$$$fl;'drkl");
            new readNotDecode(socket).start();
        } else {
            String userName = "tao la sep2#e2dddr44faDKRd$$$fl;'drkl";
            dataOutputStream.writeUTF(userName);
            new ClientRead(socket).run();
        }
    }
}