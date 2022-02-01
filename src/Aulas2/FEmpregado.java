package Aulas2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.PrivateKey;
import java.sql.*;

public class FEmpregado {
    private JPanel panel1;
    private JTextField textFieldId;
    private JTextField textFieldNome;
    private JTextField textFieldSalario;
    private JLabel LabelImagem;
    private JButton buttonSalvar;
    private JButton buttonProcurar;
    private String path = null;
    private byte[] userImage;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;



    public FEmpregado() {

        buttonProcurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            JFileChooser imgChooser = new JFileChooser();
            imgChooser.showOpenDialog(null);
            File file = imgChooser.getSelectedFile();

            path =  file.getAbsolutePath();
                BufferedImage img;
                try{
                    img = ImageIO.read(imgChooser.getSelectedFile());
                    ImageIcon imageIcon = new ImageIcon( new ImageIcon(img).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    LabelImagem.setIcon(imageIcon);
                    //guardar imagem na variavel byte[] userImage para ser depois guardada na base de dados
                    File image = new File(path);
                    FileInputStream fs = new FileInputStream(image);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int nBytesRead = 0;
                    while ((nBytesRead = fs.read(buff)) != -1)
                    {
                        bos.write(buff, 0,nBytesRead);
                    }

                    userImage = bos.toByteArray();


                }catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });

        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = textFieldNome.getText();
                double salario = Double.valueOf(textFieldSalario.getText());
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/bdempregados", "root", "1234");
                    ps = con.prepareStatement("Insert into empregados(Nome, salario, foto) values(?,?,?)");
                    ps.setString(1, nome);
                    ps.setDouble(2, salario);
                    ps.setBytes(3, userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Empregado Alterado com sucesso!");
                    textFieldId.setText("");
                    textFieldNome.setText("");
                    textFieldSalario.setText("");
                    LabelImagem.setIcon(null);
                }catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public static void setVisible(boolean b){

        JFrame frame = new JFrame("Gestão de Clubes");
        frame.setContentPane(new FEmpregado().panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(600, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
