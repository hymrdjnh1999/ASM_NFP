package app.client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class writeNotEncode extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public writeNotEncode(Socket socket2) {
        this.socket = socket2;
    }

    String checkUserNameEmpty() {
        String user = "";
        do {
            user = scanner.nextLine();
        } while (user == null || user.trim().isEmpty());
        return user;
    }

    @Override
    public void run() {
        try {
            sleep(70);
            System.out.print("Enter your name : ");
            String userName = checkUserNameEmpty();
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(userName);
            String mess;
            do {
                mess = scanner.nextLine();
                dataOutputStream.writeUTF(mess);
            } while (!mess.equals("bye"));
            MenuChatClient.mainMenu();
        } catch (Exception e) {
        }
    }
}