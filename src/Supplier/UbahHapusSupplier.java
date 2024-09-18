package Supplier;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UbahHapusSupplier {
    private JTextField txtidsupplier;
    private JTextField txtnama;
    private JTextField txtnotelp;
    private JButton btnUbah;
    private JTextArea taalamat;
    private JTextField txtemail;
    private JButton btnHapus;
    private JPanel ubahhapussupplier;
    private JButton btnCari;
    private JButton btnBersihkan;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idsupplier;
    String nama;
    String alamat;
    String noTelp;
    String email;

    public UbahHapusSupplier() {
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idsupplier = txtidsupplier.getText();
                nama = txtnama.getText();
                alamat = taalamat.getText();
                noTelp = txtnotelp.getText();
                email = txtemail.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            String query = "UPDATE Supplier SET nama=?,alamat=?,noTelp=?,email=? where id_supplier = ? ";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, alamat);
                            connection.pstat.setString(3, noTelp);
                            connection.pstat.setString(4, email);
                            connection.pstat.setString(5, idsupplier);
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
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idsupplier = txtidsupplier.getText();
                try{
                    String query = "EXEC sp_DeleteSupplier @id_supplier=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1,idsupplier);
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
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idsupplier;
                idsupplier = txtidsupplier.getText();
                try {
                    String query = "SELECT id_supplier,nama,alamat,noTelp,email from Supplier where id_supplier = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idsupplier);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtnama.setText("");
                        taalamat.setText("");
                        txtnotelp.setText("");
                        txtemail.setText("");
                    }
                    else
                    {
                       txtnama .setText(result1.getString("nama"));
                        taalamat .setText(result1.getString("alamat"));
                        txtnotelp .setText(result1.getString("noTelp"));
                        txtemail .setText(result1.getString("email"));
                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat cari: " + ex);
                }
            }
        });
    }

    public boolean validasinull(){
        if(idsupplier.isEmpty()||nama.isEmpty()||alamat.isEmpty()||noTelp.isEmpty()||email.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public void clear(){
        txtidsupplier.setText("");
        txtnama.setText("");
        taalamat.setText("");
        txtnotelp.setText("");
        txtemail.setText("");
    }

    public void uhs() {
        JFrame frame = new JFrame("UbahHapusSupplier");
        frame.setContentPane(new UbahHapusSupplier().ubahhapussupplier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
