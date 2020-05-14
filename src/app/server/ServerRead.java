package app.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;

import app.Encode;

public class ServerRead extends Thread {
    Socket socket;

    protected ServerRead(Socket socket) {
        this.socket = socket;

    }

    private static String decode(String mess) {
        mess = mess.replace("v0oibe+nh/o", "");
        mess = mess.replace("hj32da88hardCode", "");
        return new String(Base64.getDecoder().decode(mess));
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ChatServer.printUser();
            String userName = dataInputStream.readUTF();
            String decode = decode(userName);
            String decodeUserName = decode;
            ChatServer.userNames.add(decode);
            String reportConnect = decode + " connected to server";
            System.out.println(reportConnect);
            String encode = Encode.encode(reportConnect);
            ChatServer.sendMessageToAllClient(encode, socket);
            while (true) {
                String read = dataInputStream.readUTF();
                decode = decode(read);
                encode = Encode.encode(decodeUserName + " : " + decode);
                ChatServer.sendMessageToAllClient(encode, socket);
                if (decode.equals("bye")) {
                    ChatServer.userNames.remove(decodeUserName);
                    System.out.println(decodeUserName + " has quitted");

                    encode = Encode.encode(decodeUserName + " has quitted");
                    ChatServer.sendMessageToAllClient(encode, socket);
                    break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

}
