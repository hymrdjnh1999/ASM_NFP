package app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import app.Encode;

public class ServerRead extends Thread {
    Socket socket;

    protected ServerRead(Socket socket) {
        this.socket = socket;

    }

    private static String decode(String mess) {
        mess = new String(Base64.getDecoder().decode(mess));
        mess = mess.replace("==v0oibe+nh/o", "");
        mess = mess.replace("hj32da88hardCode", "");
        return mess;
    }

    void sendChatLogToNewUser() {
        for (String string : ChatServer.chatLog) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(string);
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void run() {
        String decodeUserName = "";
        String encode = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String select = dataInputStream.readUTF();
            if (select.equals("1")) {
                ChatServer.listUserNotEncode.add(socket);
                ServerNotEncode serverNotEncode = new ServerNotEncode(socket);
                serverNotEncode.start();
            } else {
                ChatServer.listUser.add(socket);
                ChatServer.printUser();
                sendChatLogToNewUser();
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
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String timeSend = dtf.format(LocalTime.now()).toString();
                    decode = decode(read);
                    encode = Encode.encode("[" + timeSend + "] " + decodeUserName + " : " + decode);
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
            }
        } catch (IOException e) {
        }
    }

}
