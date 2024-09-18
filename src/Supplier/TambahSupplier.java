package Supplier;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TambahSupplier {
    private JTextField txtidsupplier;
    private JTextField txtnama;
    private JButton btnTambah;
    private JTextArea taalamat;
    private JTextField txtemail;
    private JTextField txtnotelp;
    private JPanel tambahsupplier;
    private JButton btnBersihkan;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idsupplier;
    String nama;
    String alamat;
    String noTelp;
    String email;

    public TambahSupplier() {
        model = new DefaultTableModel();
        autoid();
        //addColumn();
        btnBersihkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        txtnotelp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(txtnotelp.getText().length() >= 13){
                    e.consume();
                }
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '\b') {
                    txtnotelp.setEditable(true);
                }else {
                    txtnotelp.setEditable(false);
                }
            }
        });
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idsupplier = txtidsupplier.getText();
                nama = txtnama.getText();
                alamat = taalamat.getText();
                noTelp = txtnotelp.getText();
                email = txtemail.getText();

                try {
                    if (validasinull()) {
                        throw new Exception("Harap Isi Semua Data!");
                    } else {
                        try {
                            String query = "EXEC sp_InsertSupplier @id_supplier=?,@nama=?,@alamat=?,@noTelp=?,@email=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, idsupplier);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, alamat);
                            connection.pstat.setString(4, noTelp);
                            connection.pstat.setString(5, email);
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
        model.addColumn("ID Supplier");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("NO Telp");
        model.addColumn("Email");
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
        autoid();
    }
    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_supplier,3))+1 FROM Supplier";
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
                kode = "SPL00"+autoid;
            }else if(autoid<100){
                kode = "SPl0"+autoid;
            }else{
                kode = "SPL"+autoid;
            }
            txtidsupplier.setText(kode);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void ts() {
        JFrame frame = new JFrame("TambahSupplier");
        frame.setContentPane(new TambahSupplier().tambahsupplier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
