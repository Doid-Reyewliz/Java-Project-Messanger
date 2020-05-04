package network;

import java.net.Socket;
import java.io.*;
import java.lang.Thread;
import java.nio.charset.Charset;

public class Connection {

    private final Socket socket;
    private final Thread rxThread;
    private final ConnectionListener evenListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public Connection(ConnectionListener evenListener, String ipAddr, int port) throws IOException {
        this(evenListener, new Socket(ipAddr, port));
    }

    public Connection(ConnectionListener evenListener, Socket socket) throws IOException {
        this.evenListener = evenListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    evenListener.onConnectionReady(Connection.this);
                    while (!rxThread.isInterrupted()) {
                        evenListener.onReceiveString(Connection.this, in.readLine());
                    }
                } catch (IOException e) {
                    evenListener.onException(Connection.this, e);

                } finally {
                    evenListener.onDisconnect(Connection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            evenListener.onException(Connection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            evenListener.onException(Connection.this, e);
        }
    }

    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}