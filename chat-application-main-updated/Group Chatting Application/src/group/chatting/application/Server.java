package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Server implements Runnable {
    
    Socket socket;
    private static CountDownLatch clientsInitialized;
    private static UserOne userOne;
    private static UserTwo userTwo;
    private static UserThird userThird;

    static Map<Socket, String> socketToUsername = new HashMap<>();
    public static Vector client = new Vector();
    
    public Server (Socket socket) {
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

