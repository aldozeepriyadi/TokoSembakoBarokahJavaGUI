package Satuan;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UbahHapusSatuan {
    private JPanel ubahapusatuan;
    private JTextField txtidsatuan;
    private JTextField txtsatuan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersihkan;
    private JButton btnCari;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idsatuan;
    String satuan;

    public UbahHapusSatuan() {
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idsatuan = txtidsatuan.getText();
                satuan = txtsatuan.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            String query = "UPDATE Satuan SET satuan=? where id_satuan = ? ";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, satuan);
                            connection.pstat.setString(2, idsatuan);
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
                idsatuan = txtidsatuan.getText();
                try{
                    String query = "EXEC sp_DeleteSatuan @id_satuan=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1,idsatuan);

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
                idsatuan= txtidsatuan.getText();
                try {
                    String query = "SELECT id_satuan,satuan from Satuan where id_satuan = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idsatuan);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtsatuan.setText("");
                    }
                    else
                    {
                        txtsatuan.setText(result1.getString("satuan"));
                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat insert data Jabatan: " + ex);
                }
            }
        });
        btnBersihkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }
    public boolean validasinull(){
        if(idsatuan.isEmpty()||satuan.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public void clear(){
        txtidsatuan.setText("");
        txtsatuan.setText("");
    }

    public void uhs() {
        JFrame frame = new JFrame("Ubah Hapus Satuan");
        frame.setContentPane(new UbahHapusSatuan().ubahapusatuan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
