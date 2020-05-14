package app.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import app.server.ChatServer;

public class MonitorClient {
    static Socket socket;
    static String hostName = "";
    static int port = -1;

    static void checkSocketIsCorrect() throws UnknownHostException, IOException {
        Scanner scanner = new Scanner(System.in);

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
        new DataOutputStream(socket.getOutputStream()).writeUTF("tao la sep2#e2dddr44faDKRd$$$fl;'drkl");
        new ClientRead(socket).run();

    }
}