package Hadiah;

import DBConnection_06.DBConnection_06;
import Form.FormHadiah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TambahHadiah {
    private JTextField txtidhadiah;
    private JTextField txthadiah;
    private JTextField txtstok;
    private JButton btnTambah;
    private JButton btnKembali;
    private JButton btnBersihkan;
    private JTextField txtpoin;
    public JPanel TambahHadiah;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idhadiah;
    String hadiah;
    String stokhadiah;
    String poin;

    public TambahHadiah() {
        model = new DefaultTableModel();
        autoid();
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idhadiah = txtidhadiah.getText();
                hadiah = txthadiah.getText();
                stokhadiah = txtstok.getText();
                poin = txtpoin.getText();
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else{
                        try{
                            String query = "EXEC sp_InsertHadiahMember @id_hadiah=?,@hadiah=?,@stok_hadiah=?,@poin=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1,idhadiah);
                            connection.pstat.setString(2,hadiah);
                            connection.pstat.setString(3,stokhadiah);
                            connection.pstat.setString(4,poin);
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
        autoid();
    }

    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_hadiah,3))+1 FROM HadiahMember";
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
                kode = "HDM00"+autoid;
            }else if(autoid<100){
                kode = "HDM0"+autoid;
            }else{
                kode = "HDM"+autoid;
            }
            txtidhadiah.setText(kode);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void TH() {
        JFrame frame = new JFrame("TambahHadiah");
        frame.setContentPane(new TambahHadiah().TambahHadiah);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
