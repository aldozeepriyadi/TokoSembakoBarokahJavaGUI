import javax.swing.*;

import  DBConnection_06.DBConnection_06;
import DSPemilik.DSPemilik;
import Dashboard.DashboardAdmin;
import Dashboard.DashboardKasir;
import  Merk.TambahMerk;
import Pengguna.MasterPengguna;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class Login {
    private JPanel Panel;
    private JPanel Login;
    private JLabel Label_Username;
    private JTextField TxtUsername;
    private JPasswordField TxtPassword;
    private JLabel Label_Password;
    private JCheckBox Checkbox_Password;
    private JPanel Panel_Tombol;
    private JButton Tombol_Login;
    private JButton Tombol_Keluar;
    private JPanel Panel_Judul;
    private JLabel Label_Judul;
    private JPanel Panel_Bantuan;
    public JPanel login;
    public String nama,jbt;

    DBConnection_06 connection_06 = new DBConnection_06();

    public Login() {

        Checkbox_Password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Checkbox_Password.isSelected() == true) {
                    TxtPassword.setEchoChar('\0');
                } else {
                    TxtPassword.setEchoChar('*');
                }
            }
        });
//        Tombol_Login.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                String[] value = validasi();
////
////                Boolean valid = Boolean.parseBoolean(value[0]);
////
////                if (valid) {
////                    //JOptionPane.showMessageDialog(formLogin, "Selamat Datang di Aplikasi TokoSembako Barokah", "Information", JOptionPane.INFORMATION_MESSAGE);
////
////                    if (value[2].equals("Pengguna")) {
////                        JOptionPane.showMessageDialog(Login, "Selamat Datang Pengguna", "Information", JOptionPane.INFORMATION_MESSAGE);
////
////                        MasterPengguna form = new MasterPengguna(value);
////
////                        form.setVisible(true);
////                    }
////                }
//                int result = 0;
//                String jabatan = "";
//                if (TxtUsername.getText().isEmpty() && TxtPassword.getText().isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Masukkan Username & Password");
//                } else if (TxtPassword.getText().isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Masukkan Password");
//
//                } else if (TxtUsername.getText().isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Masukkan Username");
//                } else {
//
//                    try {
//                        //DBConnection_06 connection_06 = new DBConnection_06();
//                        connection_06.stat = connection_06.conn.createStatement();
//                        String query = "SELECT * FROM Pengguna WHERE username ='" + TxtUsername.getText() + "' AND pass='" + TxtPassword.getText() + "'";
//                        connection_06.result = connection_06.stat.executeQuery(query);
//
//
//                        if (connection_06.result.next()) {
//                            try {
//                                String id = connection_06.result.getString("id_jabatan");
//                                String idPgw=connection_06.result.getString("id_pengguna");
//                                nama=connection_06.result.getString("nama");
//                                String JBT = "SELECT * FROM Jabatan WHERE id_jabatan ='" + id + "'";
//                                connection_06.result = connection_06.stat.executeQuery(JBT);
//                                if (connection_06.result.next())
//                                    try {
//                                        jbt = connection_06.result.getString("nama_jabatan");
//                                        if (jbt.equals("Pemilik")) {
//                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Manager");
//                                            login.removeAll();
//                                            login.revalidate();
//                                            login.repaint();
//                                            DSPemilik p = new DSPemilik();
//                                            p.dspemilik.setVisible(true);
////                                            p.lblNama.setText(nama);
////                                            p.lblJabatan.setText(jbt);
//                                            login.revalidate();
//                                            login.setLayout(new java.awt.BorderLayout());
//                                            login.add(p.dspemilik);
//
//                                        } else if (jbt.equals("Admin")) {
//                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Admin");
//                                            login.removeAll();
//                                            login.revalidate();
//                                            login.repaint();
//                                            DashboardAdmin p = new DashboardAdmin();
//                                            p.dsadmin.setVisible(true);
////                                            p.lblNama.setText(nama);
////                                            p.lblJabatan.setText(jbt);
//                                            login.revalidate();
//                                            login.setLayout(new java.awt.BorderLayout());
//                                            login.add(p.dsadmin);
//
//                                        } else if (jbt.equals("Kasir")) {
//                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Kasir");
//                                            login.removeAll();
//                                            login.revalidate();
//                                            login.repaint();
//                                            DashboardKasir p = new DashboardKasir();
//                                            p.DSKasir.setVisible(true);
////                                            p.lblNama.setText(nama);
////                                            p.lblJabatan.setText(jbt);
//                                            p.idpgw=idPgw;
//                                            login.revalidate();
//                                            login.setLayout(new java.awt.BorderLayout());
//                                            login.add(p.DSKasir);
//                                        } else {
//                                            JOptionPane.showMessageDialog(null, "Login Gagal!" + "\nJabatan Tidak Tersedia!");
//                                        }
//                                    } catch (Exception e5) {
//
//                                    }
//                            }catch (Exception a1){
//
//                            }
//
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Login Gagal!" + "\nInputkan Username & Password yang Benar!");
//                        }
//
//                    } catch (SQLException ex) {
//
//                    }
//                }
//            }
//        });
        Tombol_Keluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        TxtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (TxtUsername.getText().length() >= 15) {
                    e.consume();
                }
            }
        });
        TxtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (TxtPassword.getText().length() >= 15) {
                    e.consume();
                }
            }
        });
        Tombol_Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = 0;
                String jabatan = "";
                if (TxtUsername.getText().isEmpty() && TxtPassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Masukkan Username & Password");
                } else if (TxtPassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Masukkan Password");

                } else if (TxtUsername.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Masukkan Username");
                } else {

                    try {
                        //DBConnection_06 connection_06 = new DBConnection_06();
                        connection_06.stat = connection_06.conn.createStatement();
                        String query = "SELECT * FROM Pengguna WHERE username ='" + TxtUsername.getText() + "' AND pass='" + TxtPassword.getText() + "'";
                        connection_06.result = connection_06.stat.executeQuery(query);


                        if (connection_06.result.next()) {
                            try {
                                String id = connection_06.result.getString("id_jabatan");
                                String idPgw=connection_06.result.getString("id_pengguna");
                                nama=connection_06.result.getString("nama");
                                String JBT = "SELECT * FROM Jabatan WHERE id_jabatan ='" + id + "'";
                                connection_06.result = connection_06.stat.executeQuery(JBT);
                                if (connection_06.result.next())
                                    try {
                                        jbt = connection_06.result.getString("nama_jabatan");
                                        if (jbt.equals("Pemilik")) {
                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Manager");
                                            login.removeAll();
                                            login.revalidate();
                                            login.repaint();
                                            DSPemilik p = new DSPemilik();
                                            p.dspemilik.setVisible(true);
//                                            p.lblNama.setText(nama);
//                                            p.lblJabatan.setText(jbt);
                                            login.revalidate();
                                            login.setLayout(new java.awt.BorderLayout());
                                            login.add(p.dspemilik);

                                        } else if (jbt.equals("Admin")) {
                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Admin");
                                            login.removeAll();
                                            login.revalidate();
                                            login.repaint();
                                            DashboardAdmin p = new DashboardAdmin();
                                            p.dsadmin.setVisible(true);
//                                            p.lblNama.setText(nama);
//                                            p.lblJabatan.setText(jbt);
                                            login.revalidate();
                                            login.setLayout(new java.awt.BorderLayout());
                                            login.add(p.dsadmin);

                                        } else if (jbt.equals("Kasir")) {
                                            JOptionPane.showMessageDialog(null, "Berhasil Login Sebagai Kasir");
                                            login.removeAll();
                                            login.revalidate();
                                            login.repaint();
                                            DashboardKasir p = new DashboardKasir();
                                            p.DSKasir.setVisible(true);
//                                            p.lblNama.setText(nama);
//                                            p.lblJabatan.setText(jbt);
                                            p.idpgw=idPgw;
                                            login.revalidate();
                                            login.setLayout(new java.awt.BorderLayout());
                                            login.add(p.DSKasir);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Login Gagal!" + "\nJabatan Tidak Tersedia!");
                                        }
                                    } catch (Exception e5) {

                                    }
                            }catch (Exception a1){

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Login Gagal!" + "\nInputkan Username & Password yang Benar!");
                        }

                    } catch (SQLException ex) {

                    }
                }
            }
        });
    }

//    public String[] validasi(){
//        if(TxtUsername.getText().isEmpty()|| TxtPassword.getText().isEmpty()){
//            JOptionPane.showMessageDialog(Login,"Username / Password Kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
//        }else {
//
//            try {
//                DBConnection_06 connection = new DBConnection_06();
//
//                connection.stat = connection.conn.createStatement();
//
//                String query = "SELECT * FROM tbPegawai WHERE username = '" +TxtUsername.getText()+"' and password = '" +TxtPassword.getText()+"'";
//
//                connection.result = connection.stat.executeQuery(query);
//
//                if(!connection.result.next()){
//                    throw  new Exception("Pengguna Tidak Ditemukan");
//                }
//
//                String username = connection.result.getString(7);
//                String password = connection.result.getString(8);
//
//                return new String[] {"true", username,password};
//            }catch (Exception ex){
//
//                System.out.println(ex.getMessage());
//                JOptionPane.showMessageDialog(Login,ex.getMessage(), "Peringatan",JOptionPane.WARNING_MESSAGE);
//            }
//        }
//        return new String[] {"false"};
//    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

