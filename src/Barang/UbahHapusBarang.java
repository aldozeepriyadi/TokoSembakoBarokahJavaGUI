package Barang;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UbahHapusBarang {
    private JPanel TambahHadiah;
    private JTextField txtidbarang;
    private JTextField txtnama;
    private JButton btnUbah;
    private JButton btnBersihkan;
    private JTextField txtstok;
    private JComboBox cmbmerk;
    private JTextField txthargajual;
    private JButton btnHapus;
    private JButton btnCari;
    private JPanel ubahbarang;
    private JTextField txthargagrosir;
    private JComboBox cmbSatuan;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06();
    String idbarang;
    String idmerk;
    String idSatuan;
    String nama;
    int stok;
    int hargajual;
    int hargagrosir;

    public UbahHapusBarang() {
        cmbmerk();
        cmbSatuan();
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idbarang = txtidbarang.getText();
                idmerk = (String) cmbmerk.getSelectedItem();
                idSatuan = (String) cmbSatuan.getSelectedItem();
                nama = txtnama.getText();
                stok = Integer.parseInt(String.valueOf(Integer.parseInt(txtstok.getText())));
                hargajual = Integer.parseInt(txthargajual.getText());
                hargagrosir =  Integer.parseInt(txthargagrosir.getText());

                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else {
                        try {
                            connection.stat = connection.conn.createStatement();
                            String sql0 = "SELECT id_merk FROM Merk WHERE merk = '" + cmbmerk.getSelectedItem() + "'";
                            connection.result = connection.stat.executeQuery(sql0);

                            while (connection.result.next()) {
                                idmerk = (String) connection.result.getString("id_merk");
                            }
                            connection.stat = connection.conn.createStatement();
                            String sql1 = "SELECT id_satuan FROM satuan WHERE satuan = '" + cmbSatuan.getSelectedItem() + "'";
                            connection.result = connection.stat.executeQuery(sql1);

                            while (connection.result.next()) {
                                idSatuan = (String) connection.result.getString("id_satuan");
                            }

                            String query = "UPDATE Barang SET id_merk=?,id_satuan=?,nama=?,stok=?,harga_jual=?,harga_grosir=? from Barang where id_barang=?";
                            connection.pstat = connection.conn.prepareStatement(query);

                            connection.pstat.setString(1, idmerk);
                            connection.pstat.setString(2, idSatuan);
                            connection.pstat.setString(3, nama);
                            connection.pstat.setInt(4, stok);
                            connection.pstat.setString(5, String.valueOf(hargajual));
                            connection.pstat.setString(6, String.valueOf(hargagrosir));
                            connection.pstat.setString(7, idbarang);

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
                idbarang = txtidbarang.getText();
                try{
                    String query = "EXEC sp_DeleteBarang @id_barang=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idbarang);
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
                idbarang = txtidbarang.getText();
                try {
                    connection.stat = connection.conn.createStatement();
                    String sql0 = "SELECT id_merk FROM Merk WHERE merk = '" + cmbmerk.getSelectedItem() + "'";
                    connection.result = connection.stat.executeQuery(sql0);

                    while (connection.result.next()) {
                        idmerk = (String) connection.result.getString("id_merk");
                    }
                    connection.stat = connection.conn.createStatement();
                    String sql1 = "SELECT id_satuan FROM satuan WHERE satuan = '" + cmbSatuan.getSelectedItem() + "'";
                    connection.result = connection.stat.executeQuery(sql1);

                    while (connection.result.next()) {
                        idSatuan = (String) connection.result.getString("id_satuan");
                    }
                    String query = "SELECT id_barang,id_merk,id_satuan,nama,stok,harga_jual,harga_grosir from Barang where id_barang = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idbarang);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtnama.setText("");
                        txtstok.setText("");
                        txthargajual.setText("");
                        txthargagrosir.setText("");
                    }
                    else
                    {
                        cmbmerk.setSelectedItem(result1.getString("id_merk"))  ;
                        cmbSatuan.setSelectedItem(result1.getString("id_satuan"))  ;
                        txtnama .setText(result1.getString("nama"));
                        txtstok.setText(result1.getString("stok"));
                        txthargajual.setText(result1.getString("harga_jual"));
                        txthargagrosir.setText(result1.getString("harga_grosir"));
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
    }

    public boolean validasinull(){
        if(idbarang.isEmpty()||idmerk.isEmpty() ||nama.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void clear(){
        txtidbarang.setEnabled(false);
        txtnama.setText("");
        txtstok.setText("");
        txthargajual.setText("");
        txthargagrosir.setText("");
        cmbmerk.setSelectedItem(null);
    }
    private void cmbmerk() {
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_merk,merk FROM Merk";
            connection.result = connection.stat.executeQuery(sql);

            while(connection.result.next()){
                cmbmerk.addItem(connection.result.getString("merk"));
            }
            connection.stat.close();
            connection.result.close();
        }catch (SQLException ex){
            System.out.println("Terjadi error saat load data" +ex);
        }
    }
    private void cmbSatuan(){
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_satuan,satuan FROM satuan";
            connection.result = connection.stat.executeQuery(sql);

            while(connection.result.next()){
                cmbSatuan.addItem(connection.result.getString("satuan"));
            }
            connection.stat.close();
            connection.result.close();
        }catch (SQLException ex){
            System.out.println("Terjadi error saat load data" +ex);
        }
    }

    public void UHB() {
        JFrame frame = new JFrame("UbahHapusBarang");
        frame.setContentPane(new UbahHapusBarang().ubahbarang);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
