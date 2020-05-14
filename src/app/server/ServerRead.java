package app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

    void sendChatLogToNewUser() {
        for (String string : ChatServer.chatLog) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String decodeUserName = "";
        String encode = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ChatServer.printUser();

            sendChatLogToNewUser();

            writer.print("Enter your name : ");
            String userName = dataInputStream.readUTF();
            if (!decodeUserName.equals("tao la sep2#e2dddr44faDKRd$$$fl;'drkl")) {
                String decode = decode(userName);
                decodeUserName = decode;
                ChatServer.userNames.add(decode);
                String reportConnect = decode + " connected to server";

                System.out.println(reportConnect);
                encode = Encode.encode(reportConnect);
                ChatServer.sendMessageToAllClient(encode, socket);
            }
            while (true) {
                String decode;
                String read = dataInputStream.readUTF();
                decode = decode(read);
                encode = Encode.encode(decodeUserName + " : " + decode);
                ChatServer.chatLog.add(encode);
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
