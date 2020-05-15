package app.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class readNotDecode extends Thread {
    private Socket socket;
    DataInputStream dataInputStream;

    public readNotDecode(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                String Message = dataInputStream.readUTF();

                if (Message.isEmpty()) {
                    return;
                }
                System.out.println(Message);

            }
        } catch (IOException e1) {

        }

    }
}