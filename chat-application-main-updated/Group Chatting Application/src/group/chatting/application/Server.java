package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * This class represents a server for a chat application. It handles incoming client connections,
 * message broadcasting to all clients, and client sign-out.
 */
public class Server implements Runnable {
    
    Socket socket;
    private static CountDownLatch clientsInitialized;
    private static UserOne userOne;
    private static UserTwo userTwo;
    private static UserThird userThird;

    // Mapping from sockets to usernames to keep track of who is connected
    static Map<Socket, String> socketToUsername = new HashMap<>();

    // List of all client writers to broadcast messages
    public static Vector client = new Vector();

    /**
     * Constructor that sets the client socket.
     * @param socket The socket associated with a client connection.
     */
    public Server (Socket socket) {
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run method to handle client logic, reads data from client, and broadcasts messages.
     */
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Read the username from the client
            String username = reader.readLine().trim();
            socketToUsername.put(socket, username);


            client.add(writer);
            
            while(true) {
                String data = reader.readLine().trim();
                System.out.println("Received " + data);

                if (data.equals("/signout")) {
                    handleSignOut(writer, socket);
                    break;
                }

                // Broadcast the message to all clients
                for (int i = 0; i < client.size(); i++) {
                    try {
                        BufferedWriter bw = (BufferedWriter) client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Handles client sign-out, removes them from the broadcast list, and closes their socket.
     * @param writer The BufferedWriter associated with the client's socket.
     * @param socket The client's socket.
     */
    private void handleSignOut(BufferedWriter writer, Socket socket) {
        try {
            client.remove(writer);
            String username = socketToUsername.remove(socket);
            System.out.println(username + " has signed out.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to set up server and handle incoming client connections.
     * It initializes necessary resources and listens on a server socket.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(2003);
        clientsInitialized = new CountDownLatch(3); // 3 client instances

        // Create and start threads for initializing client instances
        Thread userOneThread = new Thread(() -> {
            userOne = new UserOne();
            clientsInitialized.countDown();
        });
        Thread userTwoThread = new Thread(() -> {
            userTwo = new UserTwo();
            clientsInitialized.countDown();
        });
        Thread userThirdThread = new Thread(() -> {
            userThird = new UserThird();
            clientsInitialized.countDown();
        });

        userOneThread.start();
        userTwoThread.start();
        userThirdThread.start();

        // Wait for all client instances to be initialized
        clientsInitialized.await();

        // Start threads for client instances
        Thread userOneClientThread = new Thread(userOne);
        Thread userTwoClientThread = new Thread(userTwo);
        Thread userThirdClientThread = new Thread(userThird);
        userOneClientThread.start();
        userTwoClientThread.start();
        userThirdClientThread.start();
        while(true) {
            Socket socket = s.accept();
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}

