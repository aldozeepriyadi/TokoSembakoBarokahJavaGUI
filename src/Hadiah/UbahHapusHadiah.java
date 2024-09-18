package Hadiah;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UbahHapusHadiah {
    private JTextField txtidhadiah;
    private JTextField txthadiah;
    private JTextField txtstok;
    private JButton btnUbah;
    private JButton btnBersihkan;
    private JTextField txtpoin;
    private JButton btnHapus;
    private JButton btnCari;
    private JPanel UbahHapusHadiah;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idhadiah;
    String hadiah;
    String stokhadiah;
    String poin;

    public UbahHapusHadiah() {
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idhadiah = txtidhadiah.getText();
                hadiah = txthadiah.getText();
                stokhadiah = txtstok.getText();
                poin = txtpoin.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            String query = "UPDATE HadiahMember SET hadiah=?,stok_hadiah=?,poin=? from HadiahMember where id_hadiah=?";
                            connection.pstat = connection.conn.prepareStatement(query);

                            connection.pstat.setString(1, hadiah);
                            connection.pstat.setString(2, stokhadiah);
                            connection.pstat.setString(3, poin);
                            connection.pstat.setString(4, idhadiah);
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
                idhadiah = txtidhadiah.getText();
                try {
                        String query = "DELETE HadiahMember from HadiahMember where id_hadiah=? ";
                        connection.pstat = connection.conn.prepareStatement(query);
                        connection.pstat.setString(1, idhadiah);
                        connection.pstat.executeUpdate();
                        connection.pstat.close();
                        JOptionPane.showMessageDialog(null, "Delete Data Berhasil");
                        clear();
                    } catch (Exception ex) {
                        System.out.println("Terjadi error pada saat delete data" + ex);
                    }


            }

        });
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idhadiah = txtidhadiah.getText();
                try {
                    String query = "SELECT id_hadiah,hadiah,stok_hadiah,poin from HadiahMember where id_hadiah = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idhadiah);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txthadiah.setText("");
                        txtstok.setText("");
                        txtpoin.setText("");
                    }
                    else
                    {
                        txthadiah .setText(result1.getString("hadiah"));
                        txtstok .setText(result1.getString("stok_hadiah"));
                        txtpoin .setText(result1.getString("poin"));

                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat cari: " + ex);
                }
            }
        });
        btnBersihkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    public boolean validasinull(){
        if(idhadiah.isEmpty()||hadiah.isEmpty() || stokhadiah.isEmpty()||poin.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void clear(){
        txtidhadiah.setText("");
        txthadiah.setText("");
        txtstok.setText("");
        txtpoin.setText("");
    }

    public void UHH() {
        JFrame frame = new JFrame("UbahHapusHadiah");
        frame.setContentPane(new UbahHapusHadiah().UbahHapusHadiah);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
