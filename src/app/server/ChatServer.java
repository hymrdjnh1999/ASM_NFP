package app.server;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private int port = 8818;
    private Set<String> userNames = new HashSet<String>();
    private Set<UserThread> userThreads = new HashSet<UserThread>();
    private DataOutputStream dataOutputStream;

    public ChatServer(int port) {
        this.port = port;

    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("waiting client connection on port : " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("New user connected " + socket.getInetAddress());
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();

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

    void sendMessageToAllClient(String message, UserThread excludeUser) {
        for (UserThread user : this.userThreads) {
            if (!user.equals(excludeUser)) {
                user.sendMessage(message, dataOutputStream);
            }
        }

    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void removeUser(String userName, UserThread user) {
        boolean removeUserName = this.userNames.remove(userName);
        if (removeUserName) {
            userThreads.remove(user);
            System.out.println("The user " + userName + " disconnect!");
        }
    }

    /**
     * @return the userName
     */
    public Set<String> getUserName() {
        return this.userNames;
    }

    boolean hasUser() {
        return this.userThreads.isEmpty();
    }
}