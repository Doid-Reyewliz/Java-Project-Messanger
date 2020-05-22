package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import network.ConnectionListener;

public class Server implements ConnectionListener {

    public static void main(String[] args) {
        new Server();
    }

    private final ArrayList<network.Connection> connections = new ArrayList<>();

    public Server() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                try {
                    new network.Connection(this, serverSocket.accept());

                } catch (IOException e) {
                    System.out.println("Connection exception:" + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(network.Connection connection) {
        connections.add(connection);
        sentToAllConnections("Client connected: " + connection);
    }

    @Override
    public synchronized void onReceiveString(network.Connection connection, String value) {
        sentToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(network.Connection connection) {
        connections.remove(connection);
        sentToAllConnections("Client disconnected: " + connection);    
    }

    @Override
    public synchronized void onException(network.Connection connection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    private void sentToAllConnections(String value){
        System.out.println(value);
        connections.size();
        for(int i = 0; i < connections.size(); i++) connections.get(i).sendString(value);
    }
}