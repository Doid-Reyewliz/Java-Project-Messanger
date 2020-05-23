package database;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class Forgot extends JFrame {

    private static final long serialVersionUID = -7025433544363129040L;

    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    private String qu[] = { "Choose question", "Name of your first pet", "Your University", "Favorite country" };

    private JPanel p = new JPanel();

    private JLabel l = new JLabel("Reset Password");
    private JLabel l0 = new JLabel();

    private JTextField tf1 = new JTextField(" Login");
    private JTextField tf2 = new JTextField(" Answer", 30);
    private JTextField tf3 = new JTextField(" Password ");

    private JButton b1 = new JButton("Check");
    private JButton b2 = new JButton("Back");

    private JComboBox cb = new JComboBox<>(qu);

    public static void main(String[] args) {
        new Forgot();
    }

    Forgot() {
        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        connection = Connect.ConnectDb();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame();
        f.setIconImage(new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\IITU.png").getImage());
        f.setSize(500, 400);
        f.setTitle("Reset Password");
        f.setLocation(500, 200);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setResizable(false);
        
        p.setLayout(null);

        l.setBounds(150, 50, 200, 30);
        l.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 24));
        l.setForeground(new Color(39, 48, 57)); p.add(l);

        tf1.setBounds(165, 120, 160, 24);
        tf1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf1.setForeground(Color.GRAY);
        tf1.setBackground(new Color(39, 48, 57)); 
        tf1.setToolTipText("Enter your Login");  p.add(tf1);

        cb.setBounds(165, 150, 160, 25);
        cb.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        cb.setForeground(Color.GRAY);
        cb.setBackground(new Color(39, 48, 57)); p.add(cb);

        tf2.setBounds(165, 180, 160, 25);
        tf2.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf2.setForeground(Color.GRAY);
        tf2.setBackground(new Color(39, 48, 57)); 
        tf2.setToolTipText("Enter your Answer");  p.add(tf2);

        tf3.setBounds(165, 210, 160, 25);
        tf3.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 14));
        tf3.setForeground(Color.GRAY);
        tf3.setBackground(new Color(39, 48, 57)); 
        tf3.setToolTipText("Enter your Password");  p.add(tf3);

        b1.setBounds(195, 250, 90, 30);
        b1.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 15));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(39, 48, 57)); p.add(b1);

        b2.setBounds(202, 285, 75, 30);
        b2.setFont(new Font("Bahnschrift SemiCondensed", Font.BOLD, 15));
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(39, 48, 57)); p.add(b2);
        
        l0.setBounds(0, 0, 500, 500);
        ImageIcon image = new ImageIcon("C:\\Users\\Alfa\\Desktop\\project\\images\\6.jpg");
        l0.setIcon(image); p.add(l0);

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

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String login = tf1.getText();
        
                    String query = "SELECT * FROM users where Login ='" + login + "'" ;
        
                    Statement statement = connection.createStatement();
        
                     resultSet = statement.executeQuery(query);
        
                    if (resultSet.next()){
                        JOptionPane.showMessageDialog(null,"It is login true");
        
                        String pass = resultSet.getString("Password");        
                        
                        tf3.setText(pass);
        
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorerct login");
                    }
                    String a = tf2.getText();
                    String query2 = "SELECT * FROM users where Answer = '" + a + "'";

                    preparedStatement = connection.prepareStatement(query2);
                    preparedStatement.executeQuery();

                    Statement statement2 = connection.createStatement();
                    resultSet = statement2.executeQuery(query2);

                    if (resultSet.next()){
                        String ans = resultSet.getString("Answer");
                        String pas = resultSet.getString("Password");
                        if (a.equalsIgnoreCase(ans)){
                            JOptionPane.showMessageDialog(null, "Answer is Correct");
                            tf3.setText(pas);
                        }else {
                            JOptionPane.showMessageDialog(null,"Incorrect answer");
                        }
                    }
                } catch (SQLException es) {
                    es.printStackTrace();
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