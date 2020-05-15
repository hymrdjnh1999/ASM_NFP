package app.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class readNotDecode extends Thread {
    private Socket socket;
    DataInputStream dataInputStream;

    public readNotDecode(Socket socket) {
        this.socket = socket;
        try {
            // InputStream inputStream =;
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String Message = dataInputStream.readUTF();

                if (Message.isEmpty()) {
                    return;
                }
                System.out.println(Message);

            } catch (Exception e) {

            }
        }
    }
}