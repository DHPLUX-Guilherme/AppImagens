package Aulas2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FConsulta {
    private JPanel panelConsulta;
    private JTextField textFieldID;
    private JTextField textFieldNome;
    private JTextField textFieldSalario;
    private JButton buttonUltimo;
    private JButton buttonProximo;
    private JButton buttonAnterior;
    private JButton buttonPrimeiro;
    private JLabel FotoLabel;


    private String path=null;
    private byte[] userimage;
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public void Connection() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bdempregados",
                    "root","1234");
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select id,nome,salario, foto from empregados ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public FConsulta() {

        Connection();

        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.first();
                    textFieldID.setText(String.valueOf(rs.getInt("id")));
                    textFieldNome.setText(rs.getString("nome"));
                    textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                    Blob blob = rs.getBlob("foto");
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                    ImageIcon imgIcon = new ImageIcon(new
                            ImageIcon(imageBytes).getImage().getScaledInstance(250, 250,
                            Image.SCALE_DEFAULT));
                    AbstractButton labelFoto = null;
                    FotoLabel.setIcon(imgIcon);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.last();
                    textFieldID.setText(String.valueOf(rs.getInt("id")));
                    textFieldNome.setText(rs.getString("nome"));
                    textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                    Blob blob = rs.getBlob("foto");
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    FotoLabel.setIcon(imgIcon);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        buttonProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        if(!rs.isLast()) {
                            rs.next();

                            textFieldID.setText(String.valueOf(rs.getInt("id")));
                            textFieldNome.setText(rs.getString("nome"));
                            textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                            Blob blob = rs.getBlob("foto");
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                    getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
                            FotoLabel.setIcon(imgIcon);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

            }
        });

        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rs.isFirst()) {
                        rs.previous();

                        textFieldID.setText(String.valueOf(rs.getInt("id")));

                        textFieldNome.setText(rs.getString("nome"));
                        textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                        Blob blob = rs.getBlob("foto");
                        byte[] imageBytes = blob.getBytes(1, (int)
                                blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
                        FotoLabel.setIcon(imgIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }


    public static void setVisible(boolean b){

        JFrame frame = new JFrame("Gestão de Clubes");
        frame.setContentPane(new FConsulta().panelConsulta);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(600, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
