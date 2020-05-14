package app.client;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import app.Encode;

public class ClientWrite extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public ClientWrite(Socket socket2) {
        this.socket = socket2;

        try {

            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (Exception e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {

            String userName = "";
            userName = scanner.nextLine();
            ChatClient.setUserName(userName);
            String encode = Encode.encode(userName);
            dataOutputStream.writeUTF(encode);
            System.out.print("Accept name " + userName + " type your message : ");
            String mess;
            do {
                mess = scanner.nextLine();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                String timeSend = dtf.format(LocalTime.now()).toString();
                encode = Encode.encode("[" + timeSend + "] " + mess);
                dataOutputStream.writeUTF(encode);
            } while (!mess.equals("bye"));
            ChatClient.mainMenu();
        } catch (Exception e) {
        }
    }
}
