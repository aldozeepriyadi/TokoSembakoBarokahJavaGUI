package Barang;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TambahBarang extends JFrame{
    private JPanel TambahHadiah;
    private JTextField txtidbarang;
    private JTextField txtnama;
    private JButton btnTambah;
    private JButton btnBersihkan;
    private JTextField txtstok;
    private JComboBox cmbmerk;
    private JTextField txthargajual;
    private JPanel barang;
    private JTextField txthargagrosir;
    private JComboBox cmbSatuan;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idbarang;
    String merk;
    String Satuan;
    String nama;
    int stok;
    int hargajual;
    int hargagrosir;


    public TambahBarang() {
        model = new DefaultTableModel();
        autoid();
        //comboboxSupplier();
        cmbmerk();
        cmbSatuan();
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idbarang = txtidbarang.getText();
                merk = (String) cmbmerk.getSelectedItem();
                Satuan = (String) cmbSatuan.getSelectedItem();
                nama = txtnama.getText();
                stok = Integer.parseInt(String.valueOf(Integer.parseInt(txtstok.getText())));
                hargajual = Integer.parseInt(txthargajual.getText());
                hargagrosir = Integer.parseInt(txthargagrosir.getText());
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else{
                        try{
                            connection.stat = connection.conn.createStatement();
                            String sql0 = "SELECT id_merk FROM Merk WHERE merk = '" + cmbmerk.getSelectedItem() + "'";
                            connection.result = connection.stat.executeQuery(sql0);

                            while (connection.result.next()) {
                                merk = (String) connection.result.getString("id_merk");
                            }
                            connection.stat = connection.conn.createStatement();
                            String sql11 = "SELECT id_satuan FROM satuan WHERE satuan = '" + cmbSatuan.getSelectedItem() + "'";
                            connection.result = connection.stat.executeQuery(sql11);

                            while (connection.result.next()) {
                                Satuan = (String) connection.result.getString("id_satuan");
                            }


                            String query = "EXEC sp_InsertBarang @id_barang=?,@id_merk=?,@id_satuan=?,@nama=?,@stok=?,@harga_jual=?,@harga_grosir=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1,idbarang);
                            connection.pstat.setString(2,merk);
                            connection.pstat.setString(3,Satuan);
                            connection.pstat.setString(4,nama);
                            connection.pstat.setInt(5, Integer.parseInt(String.valueOf(stok)));
                            connection.pstat.setString(6, String.valueOf(hargajual));
                            connection.pstat.setString(7, String.valueOf(hargagrosir));
                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                        }
                        catch(Exception ex) {
                            System.out.println("Terjadi error pada saat insert data"+ex);
                        }
                        JOptionPane.showMessageDialog(null,"Insert Data Berhasil");
                        clear();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
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

    public boolean validasinull(){

        if(idbarang.isEmpty()||merk.isEmpty()||nama.isEmpty()||Satuan.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void clear(){
        txtidbarang.setText("");
        txtnama.setText("");
        txtstok.setText("");
        txthargajual.setText("");
        txthargagrosir.setText("");
        cmbmerk.setSelectedItem(null);
        cmbSatuan.setSelectedItem(null);
        autoid();
    }

    public void TB() {
        JFrame frame = new JFrame("TambahBarang");
        frame.setContentPane(new TambahBarang().barang);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_barang,3))+1 FROM Barang";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.result = connection.pstat.executeQuery();
            int autoid = 0;
            while(connection.result.next()){
                if(connection.result.getString(1)==null){
                    autoid = 1;
                }else{
                    autoid =Integer.parseInt(connection.result.getString(1));
                }
            }
            String kode;
            if(autoid<10){
                kode = "BRG00"+autoid;
            }else if(autoid<100){
                kode = "BRG0"+autoid;
            }else{
                kode = "BRG"+autoid;
            }
            txtidbarang.setText(kode);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }

    }
}
