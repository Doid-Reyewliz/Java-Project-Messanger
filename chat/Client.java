package chat;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.mysql.jdbc.Connection;

import network.ConnectionListener;


public class Client extends JFrame implements ActionListener, ConnectionListener {

    private static final long serialVersionUID = 1809753839830074172L;
    
    private static final String IpAddr = "";
    private static final int Port = 1234;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
        
            @Override
            public void run() {
                new Client();
            }
        });
    }

    Connection connection1;
    PreparedStatement preparedStatement;

    private final JPanel p = new JPanel();
    private final JTextArea a = new JTextArea();
    private final JTextField tf1 = new JTextField();
    private final JButton b1 = new JButton("Send");

    private String s = "";

    private network.Connection connection;

    public Client() {

        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame();
        f.setIconImage(new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\VS.png").getImage());
        f.setSize(700, 500);
        f.setTitle("Messenger");
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        p.setBackground(new Color(29, 35, 42));
        p.setLayout(null);

        a.setBackground(new Color(29, 35, 42));
        a.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        a.setForeground(Color.WHITE);
        a.setEditable(false);
        
        JScrollPane sp = new JScrollPane(a, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBounds(0, 0, 686, 415);
        sp.setBackground(Color.BLACK);
        p.add(sp);

        tf1.setBounds(0, 430, 570, 30);
        tf1.setBackground(new Color(39, 48, 57));
        tf1.setForeground(Color.GRAY);
        tf1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        tf1.addActionListener(this);
        p.add(tf1);

        b1.setIcon(new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\send.png"));
        b1.setBounds(580, 430, 105, 30);
        b1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 12));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(39, 48, 57)); p.add(b1);

        String q = JOptionPane.showInputDialog(Client.this, new String[] {"Create Nickname"}, "Messenger", JOptionPane.QUESTION_MESSAGE);
        s = q;

        f.add(p);
        f.validate();
        f.setVisible(true);

        try {
            connection = new network.Connection(this, IpAddr, Port);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = tf1.getText();
        if(msg.equals("")) return;
        tf1.setText(null);
        connection.sendString(s + ": " + msg);
    }

    @Override
    public void onConnectionReady(network.Connection connection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(network.Connection connection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(network.Connection connection) {
        printMsg("Connection close");
    }

    @Override
    public void onException(network.Connection connection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg){
        SwingUtilities.invokeLater(new Runnable(){
        
            @Override
            public void run() {
                a.append(msg + "\n");
                a.setCaretPosition(a.getDocument().getLength());
            }
        });
    }

}