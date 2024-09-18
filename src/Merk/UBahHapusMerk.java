package Merk;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UBahHapusMerk {
    private JTextField txtidmerk;
    private JTextField txtmerk;
    private JButton btnUbah;
    private JButton btnHapus;
    private JPanel ubahhapusform;
    private JButton btnCari;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idmerk;
    String merk;

    public UBahHapusMerk() {
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idmerk = txtidmerk.getText();
                merk = txtmerk.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            String query = "UPDATE Merk SET merk=? where id_merk = ? ";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, merk);
                            connection.pstat.setString(2, idmerk);
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
                idmerk = txtidmerk.getText();
                try{
                    String query = "EXEC sp_DeleteMerk @id_merk=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1,idmerk);

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
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idmerk;
                idmerk = txtidmerk.getText();
                try {
                    String query = "SELECT id_merk,merk from Merk where id_merk = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idmerk);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtmerk.setText("");
                    }
                    else
                    {
                        txtmerk.setText(result1.getString("merk"));
                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat insert data Jabatan: " + ex);
                }
            }
        });
    }

    public boolean validasinull(){
        if(idmerk.isEmpty()||merk.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public void clear(){
        txtidmerk.setText("");
        txtmerk.setText("");

    }
    public void uhm() {
        JFrame frame = new JFrame("UBahHapusMerk");
        frame.setContentPane(new UBahHapusMerk().ubahhapusform);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UBahHapusMerk");
        frame.setContentPane(new UBahHapusMerk().ubahhapusform);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
