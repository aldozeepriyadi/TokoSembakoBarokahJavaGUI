package Satuan;

import DBConnection_06.DBConnection_06;
import Merk.TambahMerk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TambahSatuan {
    private JPanel tambahsatuan;
    private JTextField txtidsatuan;
    private JTextField txtsatuan;
    private JButton btnTambah;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idsatuan;
    String satuan;

    public boolean validasinull(){
        if(idsatuan.isEmpty()||satuan.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public TambahSatuan() {
        model = new DefaultTableModel();
        autoid();
        addColumn();
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idsatuan = txtidsatuan.getText();
                satuan = txtsatuan.getText();
                try {
                    if (validasinull()) {
                        throw new Exception("Harap Isi Semua Data!");
                    } else {
                        try {
                            String query = "EXEC sp_InsertSatuan @id_satuan=?,@satuan=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, idsatuan);
                            connection.pstat.setString(2, satuan);
                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                        } catch (Exception ex) {
                            System.out.println("Terjadi error pada saat insert data" + ex);
                        }
                        JOptionPane.showMessageDialog(null, "Insert Data Berhasil");
                        clear();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public void addColumn(){
        model.addColumn("ID Satuan");
        model.addColumn("Nama Satuan");
    }
    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_satuan,3))+1 FROM Satuan";
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
                kode = "STN00"+autoid;
            }else if(autoid<100){
                kode = "STN0"+autoid;
            }else{
                kode = "STN"+autoid;
            }
            txtidsatuan.setText(kode);
            txtsatuan.requestFocus();
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void clear(){
        txtidsatuan.setText("");
        txtsatuan.setText("");
    }

    public void ts() {
        JFrame frame = new JFrame("Tambah Satuan");
        frame.setContentPane(new TambahSatuan().tambahsatuan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
