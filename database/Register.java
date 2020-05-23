package database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class Register extends JFrame {

    private static Connection connection;
    private static PreparedStatement preparedStatement;

    private static final long serialVersionUID = 1L;

    private String qu[] = { "Choose question", "Name of your first pet", "Your University", "Favorite country" };

    private JPanel p = new JPanel();

    private JLabel l = new JLabel("Registration");
    private JLabel l0 = new JLabel();

    private JTextField tf1 = new JTextField(" Login", 30);
    private JTextField tf2 = new JTextField(" Answer", 100);
    private JTextField tf3 = new JTextField(" Password", 30);

    private JButton b1 = new JButton("Sing Up");
    private JButton b2 = new JButton("Back");

    private JComboBox cb = new JComboBox<>(qu);

    public static void main(String[] args) {
        new Register();
    }

    Register() {

        connection = Connect.ConnectDb();

        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame();
        f.setIconImage(new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\IITU.png").getImage());
        f.setSize(500, 400);
        f.setTitle("Register");
        f.setLocation(500, 200);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setResizable(false);

        p.setLayout(null);

        l.setBounds(165, 50, 170, 30);
        l.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 24));
        l.setForeground(new Color(39, 48, 57));
        p.add(l);

        tf1.setBounds(160, 120, 160, 25);
        tf1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf1.setForeground(Color.GRAY);
        tf1.setBackground(new Color(39, 48, 57));
        tf1.setToolTipText("Enter your Login"); p.add(tf1);

        cb.setBounds(160, 150, 160, 25);
        cb.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        cb.setForeground(Color.GRAY);
        cb.setBackground(new Color(39, 48, 57)); p.add(cb);

        tf2.setBounds(160, 180, 160, 25);
        tf2.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf2.setForeground(Color.GRAY);
        tf2.setBackground(new Color(39, 48, 57));
        tf2.setToolTipText("Enter your Answer"); p.add(tf2);

        tf3.setBounds(160, 210, 160, 25);
        tf3.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf3.setForeground(Color.GRAY);
        tf3.setBackground(new Color(39, 48, 57));
        tf3.setToolTipText("Enter your Password"); p.add(tf3);

        b1.setBounds(190, 250, 95, 30);
        b1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 15));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(39, 48, 57));
        p.add(b1);

        b2.setBounds(200, 290, 75, 30);
        b2.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 15));
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(39, 48, 57));
        p.add(b2);

        l0.setBounds(0, 0, 500, 500);
        ImageIcon image = new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\6.jpg");
        l0.setIcon(image);
        p.add(l0);

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

        tf3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tf3.setText("");
            }
        });

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "INSERT INTO users (Login, Password, Question, Answer) values (?,?,?,?)";

                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, tf1.getText());
                    preparedStatement.setString(2, tf3.getText());
                    preparedStatement.setString(3, (String) cb.getSelectedItem());
                    preparedStatement.setString(4, tf2.getText());

                    preparedStatement.execute();

                    JOptionPane.showMessageDialog(null, "New acoount created");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login();
                f.dispose();
            }
        });

        f.add(p);
        f.setVisible(true);
    }
}