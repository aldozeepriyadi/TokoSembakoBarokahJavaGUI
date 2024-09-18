package Merk;

import DBConnection_06.DBConnection_06;
import Form.FromMerk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TambahMerk {
    private JTextField txtidmerk;
    private JTextField txtmerk;
    private JButton btnTambah;
    private JPanel tambahmerk;
    private JButton btnKembali;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idmerk;
    String merk;

    public boolean validasinull(){
        if(idmerk.isEmpty()||merk.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }

    public TambahMerk() {
        model = new DefaultTableModel();
        autoid();
        addColumn();
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idmerk = txtidmerk.getText();
                merk = txtmerk.getText();
                try {
                    if (validasinull()) {
                        throw new Exception("Harap Isi Semua Data!");
                    } else {
                        try {
                            String query = "EXEC sp_InsertMerk @id_merk=?,@merk=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, idmerk);
                            connection.pstat.setString(2, merk);
                            connection.pstat.executeUpdate();
                            connection.pstat.close();
                            JOptionPane.showMessageDialog(null, "Insert Data Berhasil");
                            clear();

                        } catch (Exception ex) {
                            System.out.println("Terjadi error pada saat insert data" + ex);
                        }

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public void addColumn(){
        model.addColumn("ID Member");
        model.addColumn("Nama");
    }
    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_merk,3))+1 FROM Merk";
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
                kode = "MRK00"+autoid;
            }else if(autoid<100){
                kode = "MRK0"+autoid;
            }else{
                kode = "MRK"+autoid;
            }
            txtidmerk.setText(kode);
            txtmerk.requestFocus();
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void clear(){
        txtidmerk.setText("");
        txtmerk.setText("");
        autoid();
    }

    public void tm(){
        JFrame frame = new JFrame("TambahMerk");
        frame.setContentPane(new TambahMerk().tambahmerk);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TambahMerk");
        frame.setContentPane(new TambahMerk().tambahmerk);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
