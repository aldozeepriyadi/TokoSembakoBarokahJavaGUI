package Pengguna;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterPengguna extends JFrame{
    private JTextField txtNamaPengguna;
    private JTextField txtIdPengguna;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton clearButton;
    private JRadioButton lakiLakiRadioButton;
    private JPanel UbahHapusPengguna;
    private JRadioButton rbPerempuan;
    private JTextField txtNoTelpPengguna;
    private JTextField txtAlamat;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JTextField txtIDJabatan;
    private JButton CariButton;
    private JComboBox cmbJabatan;
    DBConnection_06 connect = new DBConnection_06();
    public MasterPengguna() {
        ButtonGroup group = new ButtonGroup();
        group.add(lakiLakiRadioButton);
        group.add(rbPerempuan);
        cmbJabatan();
        ubahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String jabatan = (String) cmbJabatan.getSelectedItem();
                    String status = "";
                    if(lakiLakiRadioButton.isSelected()){
                        status = lakiLakiRadioButton.getText();
                    }else if (rbPerempuan.isSelected()){
                        status = rbPerempuan.getText();
                    }
                    connect.stat = connect.conn.createStatement();
                    String sql0 = "SELECT id_jabatan FROM Jabatan WHERE nama = '" + cmbJabatan.getSelectedItem() + "'";
                    connect.result = connect.stat.executeQuery(sql0);

                    while (connect.result.next()) {
                        jabatan = (String) connect.result.getString("id_jabatan");
                    }

                    String query = "EXEC sp_UpdatePengguna @id_pengguna=?,@id_jabatan=?,@nama=?,@noTelp=?,@alamat=?,@JenisKelamin=?,@username=?,@pass=?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdPengguna.getText());
                    connect.pstat.setString(2, jabatan);
                    connect.pstat.setString(3, txtNamaPengguna.getText());
                    connect.pstat.setString(4, txtNoTelpPengguna.getText());
                    connect.pstat.setString(5, txtAlamat.getText());
                    connect.pstat.setString(6, status);
                    connect.pstat.setString(7,txtUsername.getText());
                    connect.pstat.setString(8,txtPassword.getText());
                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                    JOptionPane.showMessageDialog(null, "Update Data Berhasil");
                } catch (Exception ex) {
                    System.out.println("Terjadi error pada saat Update data" + ex);
                }
            }
        });
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String query = "EXEC sp_DeletePengguna @id_pengguna=?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdPengguna.getText());

                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                    JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
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
        CariButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT id_pengguna,id_jabatan,nama,noTelp,alamat,JenisKelamin,username,pass from Pengguna where id_pengguna = ? ";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdPengguna.getText());
                    ResultSet result1 = connect.pstat.executeQuery();
                    if (result1.next() == false) {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");

                    } else {
                        cmbJabatan.setSelectedItem(result1.getString("id_jabatan"))  ;
                        txtNamaPengguna.setText(result1.getString("nama"));
                        txtNoTelpPengguna.setText(result1.getString("noTelp"));
                        txtAlamat.setText(result1.getString("alamat"));
                        if(lakiLakiRadioButton.isSelected()){
                            lakiLakiRadioButton.setText(result1.getString("JenisKelamin"));
                        }else if (rbPerempuan.isSelected()){
                            rbPerempuan.setText(result1.getString("JenisKelamin"));
                        }
                        txtUsername.setText(result1.getString("username"));
                        txtPassword.setText(result1.getString("pass"));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat Cari data Promo: " + ex);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
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

    public void UHP() {
        JFrame frame = new JFrame("MasterPengguna");
        frame.setContentPane(new MasterPengguna().UbahHapusPengguna);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
