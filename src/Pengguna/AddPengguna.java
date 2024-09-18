package Pengguna;

import DBConnection_06.DBConnection_06;
import Jabatan.DBConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class AddPengguna extends JFrame {
    private JTextField txtNamaPengguna;
    private JTextField txtIdPengguna;
    private JButton simpanButton;
    private JButton btnClear;
    private JRadioButton lakiLakiRadioButton;
    private JPanel SimpanPengguna;
    private JTextField txtNoTelpPengguna;
    private JTextField txtAlamat;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JRadioButton rbPerempuan;
    private JComboBox cmbJabatan;
    private JTextField txtIDJabatan;
    DBConnect connect = new DBConnect();
    String idpengguna;
    String jabatan;
    String namapengguna;
    String notelp;
    String alamat;
    String username;
    String pass;

    public AddPengguna() {
        ButtonGroup group = new ButtonGroup();
        group.add(lakiLakiRadioButton);
        group.add(rbPerempuan);
        autoid();
        cmbJabatan();
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jabatan = (String) cmbJabatan.getSelectedItem();


                  try {
                      String jeniskelamin = "";
                      if(lakiLakiRadioButton.isSelected()){
                         jeniskelamin = lakiLakiRadioButton.getText();
                      }else if (rbPerempuan.isSelected()){
                          jeniskelamin = rbPerempuan.getText();
                      }
                      connect.stat = connect.conn.createStatement();
                      String sql0 = "SELECT id_jabatan FROM Jabatan WHERE nama = '" + cmbJabatan.getSelectedItem() + "'";
                      connect.result = connect.stat.executeQuery(sql0);

                      while (connect.result.next()) {
                         jabatan = (String) connect.result.getString("id_jabatan");
                      }
                      String query = "EXEC sp_InsertPengguna @id_pengguna=?,@id_jabatan=?,@nama=?,@noTelp=?,@alamat=?,@JenisKelamin=?,@username=?,@pass=?";
                      connect.pstat = connect.conn.prepareStatement(query);
                      connect.pstat.setString(1, txtIdPengguna.getText());
                      connect.pstat.setString(2, jabatan);
                      connect.pstat.setString(3, txtNamaPengguna.getText());
                      connect.pstat.setString(4, txtNoTelpPengguna.getText());
                      connect.pstat.setString(5, txtAlamat.getText());
                      connect.pstat.setString(6, jeniskelamin);
                      connect.pstat.setString(7,txtUsername.getText());
                      connect.pstat.setString(8,txtPassword.getText());
                      connect.pstat.executeUpdate();
                      connect.pstat.close();
                      JOptionPane.showMessageDialog(null, "Insert Data Berhasil");
                      } catch (Exception ex) {
                       System.out.println("Terjadi error pada saat insert data" + ex);
                      }


            }
        });
        txtNoTelpPengguna.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (txtNoTelpPengguna.getText().length() >= 13){
                    e.consume();
                }
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '\b'){
                    txtNoTelpPengguna.setEditable(true);
                }else {
                    txtNoTelpPengguna.setEditable(false);
                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }
    private void cmbJabatan() {
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_jabatan,nama FROM Jabatan";
            connection.result = connection.stat.executeQuery(sql);

            while(connection.result.next()){
                cmbJabatan.addItem(connection.result.getString("nama"));
            }
            connection.stat.close();
            connection.result.close();
        }catch (SQLException ex){
            System.out.println("Terjadi error saat load data" +ex);
        }
    }
    public void clear(){
        txtIdPengguna.setText("");
        cmbJabatan.setSelectedItem(null);
        txtNamaPengguna.setText("");
        txtNoTelpPengguna.setText("");
        txtAlamat.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        rbPerempuan.setSelected(false);
        lakiLakiRadioButton.setSelected(false);
    }
    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_pengguna,3))+1 FROM Pengguna";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.result = connect.pstat.executeQuery();
            int autoid = 0;
            while(connect.result.next()){
                if(connect.result.getString(1)==null){
                    autoid = 1;
                }else{
                    autoid =Integer.parseInt(connect.result.getString(1));
                }
            }
            String kode;
            if(autoid<10){
                kode = "PGN00"+autoid;
            }else if(autoid<100){
                kode = "PGN0"+autoid;
            }else{
                kode = "PGN"+autoid;
            }
            txtIdPengguna.setText(kode);
            connect.pstat.close();
            connect.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }
    public boolean validasinull(){
        if(idpengguna.isEmpty()||namapengguna.isEmpty()||notelp.isEmpty()|jabatan.isEmpty()||alamat.isEmpty()||username.isEmpty()||pass.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }

    public void TP() {
        JFrame frame = new JFrame("AddPengguna");
        frame.setContentPane(new AddPengguna().SimpanPengguna);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
