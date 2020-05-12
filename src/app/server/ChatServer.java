package app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private int port = 8818;
    private Set<String> userNames = new HashSet<String>();
    private Set<Socket> listUser = new HashSet<Socket>();
    private DataOutputStream dataOutputStream;
    private PrintWriter writer;

    public ChatServer(int port) {
        this.port = port;

    }

    public class ServerRead extends Thread {
        Socket socket;

        public ServerRead(Socket socket) {
            this.socket = socket;
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                printUser();
                String userName = dataInputStream.readUTF();
                userNames.add(userName);
                String reportConnect = userName + " connected to server";
                sendMessageToAllClient(reportConnect, socket);
                System.out.println(reportConnect);
                while (true) {
                    String read = dataInputStream.readUTF();
                    sendMessageToAllClient(userName + " : " + read, socket);
                    if (read.equals("bye")) {
                        removeUser(userName, socket);
                        socket.close();
                        sendMessageToAllClient(userName + " has quitted", socket);
                        System.out.println(userName + " has quitted");
                        break;
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    void printUser() {
        if (!(userNames.isEmpty())) {
            try {
                dataOutputStream.writeUTF("User connected " + getUserName());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                dataOutputStream.writeUTF("No other user connected");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("waiting client connection on port : " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ServerRead serverRead = new ServerRead(socket);
                listUser.add(socket);
                serverRead.start();

            }

        } catch (Exception e) {
            System.out.println("Error in the server " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ChatServer server = new ChatServer(8818);
        server.execute();
    }

    void sendMessageToAllClient(String message, Socket excludeUser) {
        for (Socket user : this.listUser) {
            if (!user.equals(excludeUser)) {
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(user.getOutputStream());
                    dataOutputStream.writeUTF(message);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void removeUser(String userName, Socket user) {
        boolean removeUserName = this.userNames.remove(userName);
        if (removeUserName) {
            System.out.println("The user " + userName + " disconnect!");
        }
    }

    /**
     * @return the userName
     */
    public Set<String> getUserName() {
        return this.userNames;
    }

    // boolean hasUser() {
    // return this.userThreads.isEmpty();
    // }
}