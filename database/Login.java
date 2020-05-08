package database;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import chat.Admin;
import chat.Client;

public class Login extends JFrame {

    private static final long serialVersionUID = -6740780318960842257L;

    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    private static JPanel p = new JPanel();

    private static JLabel l = new JLabel("Messenger");
    private static JLabel l0 = new JLabel();
    private static JLabel l3 = new JLabel("Forgot?");
    private static JLabel l4 = new JLabel("Register");

    private static JTextField tf1 = new JTextField(" Login");
    private static JTextField tf2 = new JTextField(" Password", 30);

    private static JButton b1 = new JButton("Login");

    public static void main(String[] args) {
        new Login();
    }

    public Login() {

        connection = Connect.ConnectDb();

        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame();
        f.setIconImage(new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\VS.png").getImage());
        f.setSize(500, 400);
        f.setTitle("Messenger");
        f.setLocation(500, 200);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        p.setBackground(new Color(29, 35, 42));
        p.setLayout(null);

        l.setBounds(175, 50, 170, 30);
        l.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 24));
        l.setForeground(new Color(39, 48, 57)); p.add(l);

        tf1.setBounds(165, 150, 150, 24);
        tf1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf1.setForeground(Color.GRAY);

        tf1.setBackground(new Color(39, 48, 57)); p.add(tf1);

        tf2.setBounds(165, 180, 150, 25);
        tf2.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf2.setForeground(Color.GRAY);
        tf2.setBackground(new Color(39, 48, 57)); p.add(tf2);

        
        l3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        l3.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 12));
        l3.setForeground(new Color(39, 48, 57));
        l3.setBounds(330, 175, 50, 30); p.add(l3);

        b1.setBounds(200, 220, 80, 30);
        b1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 15));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(39, 48, 57)); p.add(b1);

        l4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        l4.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 12));
        l4.setForeground(new Color(39, 48, 57));
        l4.setBounds(215, 250, 50, 30); p.add(l4);

        l0.setBounds(0, 0, 500, 500);
        ImageIcon image = new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\6.jpg");
        l0.setIcon(image); p.add(l0);

        l4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Register();
                f.dispose();
            }
        });

        tf1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tf1.setText("");
            }
        });
        tf2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tf2.setText("");
            }
        });
        

        l3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Forgot();
                f.dispose();
            }
        });

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT * FROM users where Login=? and Password=?";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,tf1.getText());

                    preparedStatement.setString(2,tf2.getText());

                    resultSet = preparedStatement.executeQuery();
                    if (tf1.getText().equals("admin") && tf2.getText().equals("12345")){
                        new Admin();
                        f.dispose();
                    }
                    else if (resultSet.next()){
                        resultSet.close();
                        preparedStatement.close();
                        setVisible(false);
                        new Client();
                        f.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect login or password");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        f.add(p);
        f.setVisible(true);
    }
}