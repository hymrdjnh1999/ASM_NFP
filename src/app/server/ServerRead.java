package app.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import app.EncodeDeCode;

public class ServerRead extends Thread {
    Socket socket;

    public ServerRead(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ChatServer.printUser();
            String userName = dataInputStream.readUTF();
            String decode = EncodeDeCode.decode(userName);
            String decodeUserName = decode;
            ChatServer.userNames.add(decode);
            String reportConnect = decode + " connected to server";
            System.out.println(reportConnect);
            String encode = EncodeDeCode.encode(reportConnect);
            ChatServer.sendMessageToAllClient(encode, socket);
            // System.out.println(reportConnect);
            while (true) {
                String read = dataInputStream.readUTF();
                decode = EncodeDeCode.decode(read);
                encode = EncodeDeCode.encode(decodeUserName + " : " + decode);
                ChatServer.sendMessageToAllClient(encode, socket);
                if (decode.equals("bye")) {
                    ChatServer.removeUser(userName, socket);
                    System.out.println(decodeUserName + " has quitted");
                    socket.close();
                    encode = EncodeDeCode.encode(decodeUserName + " has quitted");
                    ChatServer.sendMessageToAllClient(encode, socket);
                    break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

}
