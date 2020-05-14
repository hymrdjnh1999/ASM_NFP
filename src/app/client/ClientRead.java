package app.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import app.EncodeDeCode;

public class ClientRead extends Thread {
    private Socket socket;
    DataInputStream dataInputStream;

    public ClientRead(Socket socket) {
        this.socket = socket;
        try {
            // InputStream inputStream =;
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = dataInputStream.readUTF();
                String decode = EncodeDeCode.decode(response);
                if (decode.isEmpty()) {
                    return;
                }

                System.out.print("\n" + decode);
              
            } catch (Exception e) {
                System.out.println("Bye bye");
                System.exit(1);
                break;
            }
        }
    }
}
