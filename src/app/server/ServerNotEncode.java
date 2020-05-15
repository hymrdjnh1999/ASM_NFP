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

public class ServerNotEncode extends Thread {
    Socket socket;

    protected ServerNotEncode(Socket socket) {
        this.socket = socket;

    }

    void sendChatLogNotEncodeToNewUser() {
        for (String chatlog : ChatServer.chatLogNotEncode) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(chatlog);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
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

        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ChatServer.printUserNotEncode();
            sendChatLogNotEncodeToNewUser();
            String userName = dataInputStream.readUTF();
            if (!userName.equals("tao la sep2#e2dddr44faDKRd$$$fl;'drkl")) {
                ChatServer.userNameNotEncode.add(userName);
                String reportConnect = userName + " connected to server";
                System.out.println(reportConnect);
                ChatServer.sendMessageToAllClientNotEncode(reportConnect, socket);
            }
            while (true) {
                String read = dataInputStream.readUTF();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                String timeSend = dtf.format(LocalTime.now()).toString();
                String mess = "[" + timeSend + "] " + userName + " : " + read;
                ChatServer.chatLogNotEncode.add(mess);
                ChatServer.sendMessageToAllClientNotEncode(mess, socket);
                if (read.equals("bye")) {
                    ChatServer.userNameNotEncode.remove(userName);
                    System.out.println(userName + " has quitted");
                    mess = userName + " has quitted!";
                    ChatServer.sendMessageToAllClientNotEncode(mess, socket);
                    break;
                }
            }

        } catch (IOException e) {
        }
    }

}
