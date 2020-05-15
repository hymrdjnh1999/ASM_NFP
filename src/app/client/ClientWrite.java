package app.client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import app.Encode;

public class ClientWrite extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public ClientWrite(Socket socket2) {
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
            String encode = Encode.encode(userName);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(encode);
            String mess;
            do {
                mess = scanner.nextLine();
                encode = Encode.encode(mess);
                dataOutputStream.writeUTF(encode);
            } while (!mess.equals("bye"));
            ChatClient.mainMenu();
        } catch (Exception e) {
        }
    }
}
