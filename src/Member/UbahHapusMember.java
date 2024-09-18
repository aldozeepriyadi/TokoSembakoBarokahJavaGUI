package Member;

import DBConnection_06.DBConnection_06;
import Form.FormMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UbahHapusMember {
    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtnoTelp;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private JTextArea taalamat;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnKembali;
    private JButton btnCari;
    private JPanel ubahhapusmember;
    private JButton btnBersihkan;
    private JTextField txtpoin;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idmember;
    String nama;
    String alamat;
    String Jeniskelamin;
    String noTelp;
    String poin;

    public boolean validasinull(){
        if(idmember.isEmpty()||nama.isEmpty() || alamat.isEmpty()||Jeniskelamin.isEmpty() || noTelp.isEmpty()||poin.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public UbahHapusMember() {

        model = new DefaultTableModel();
        //addColomn();
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idmember = txtId.getText();
                nama = txtNama.getText();
                alamat = taalamat.getText();
                if (rbLaki.isSelected()){
                    Jeniskelamin = "Laki-laki";
                }else{
                    Jeniskelamin = "Perempuan";
                }
                noTelp = txtnoTelp.getText();
                poin = txtpoin.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            String query = "UPDATE Member SET nama=?,alamat=?,Jeniskelamin=?,noTelp=?,poin=? from Member where id_member=?";
                            connection.pstat = connection.conn.prepareStatement(query);

                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, alamat);
                            connection.pstat.setString(3, Jeniskelamin);
                            connection.pstat.setString(4, noTelp);
                            connection.pstat.setString(5, poin);
                            connection.pstat.setString(6, idmember);


                            connection.pstat.executeUpdate(); // insert ke database
                            connection.pstat.close(); // menutup koneksi db

                            JOptionPane.showMessageDialog(null, "Update Data  Berhasil!");
                        }
                        catch(Exception ex) {
                            System.out.println("Terjadi error pada saat update data"+ex);
                        }
                        JOptionPane.showMessageDialog(null,"Update Data Berhasil");
                        clear();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IdMember;
                IdMember = txtId.getText();
                try {
                    String query = "SELECT id_member,nama,alamat,Jeniskelamin,noTelp,poin from Member where id_member = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, IdMember);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtNama.setText("");
                        txtnoTelp.setText("");
                        taalamat.setText("");
                        rbLaki.setSelected(false);
                        rbPerempuan.setSelected(false);
                        txtpoin.setText("");

                    }
                    else
                    {
                        txtNama.setText(result1.getString("nama"));
                        txtnoTelp.setText(result1.getString("noTelp"));
                        taalamat.setText(result1.getString("alamat"));
                        if (result1.getString("Jeniskelamin").equals("Perempuan")){
                            rbPerempuan.setSelected(true);
                        }
                        else {
                            rbLaki.setSelected(true);
                        }
                        txtpoin.setText(result1.getString("poin"));
                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat insert data Jabatan: " + ex);
                }
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idmember = txtId.getText();
                try{
                    String query = "EXEC sp_DeleteMember @id_member=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1,idmember);

                    connection.pstat.executeUpdate();
                    connection.pstat.close();

                }
                catch(Exception ex) {
                    System.out.println("Terjadi error pada saat delete data"+ex);
                }
                JOptionPane.showMessageDialog(null,"Delete Data Berhasil");
                clear();
            }
        });
        btnBersihkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    public void clear(){
        txtId.setText("");
        txtNama.setText("");
        taalamat.setText("");
        rbLaki.setSelected(false);
        rbPerempuan.setSelected(false);
        txtnoTelp.setText("");
        txtpoin.setText("");
    }

    public void UHMR() {
        JFrame frame = new JFrame("UbahHapusMember");
        frame.setContentPane(new UbahHapusMember().ubahhapusmember);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
