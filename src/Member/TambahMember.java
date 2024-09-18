package Member;

import DBConnection_06.DBConnection_06;
import Form.FormMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TambahMember {
    private JPanel Member;
    private JTextField txtId;
    private JTextField txtNama;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private JButton btnTambah;
    private JTextArea taalamat;
    private JTextField txtnoTelp;
    private JPanel tambahmember;
    private JButton btnKembali;
    private JButton btnBersihkan;
    private JTextField txtpoin;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idmember;
    String nama;
    String alamat;
    String Jeniskelamin;
    String noTelp;
    String poin;

    public void addColomn(){
        model.addColumn("ID Member");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Jenis Kelamin");
        model.addColumn("No Telepon");
    }
    public boolean validasinull(){
        if(idmember.isEmpty()||nama.isEmpty() || alamat.isEmpty()||Jeniskelamin.isEmpty() || noTelp.isEmpty() || poin.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public TambahMember() {
        //membuat tabel model
        model = new DefaultTableModel();
        //menambah table model ke table
        //TableData.setModel(model);
        //addColomn();
        //loadData();
        autoid();
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idmember = txtId.getText();
                nama = txtNama.getText();
                alamat = taalamat.getText();
                if (rbLaki.isSelected()) {
                    Jeniskelamin = "Laki-laki";
                } else {
                    Jeniskelamin = "Perempuan";
                }
                noTelp = txtnoTelp.getText();
                poin = txtpoin.getText();
                try {
                    if (validasinull()) {
                        throw new Exception("Harap Isi Semua Data!");
                    } else {
                        try {
                            String query = "EXEC sp_Insertmember @id_member=?,@nama=?,@alamat=?,@Jeniskelamin=?,@noTelp=?,@poin=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, idmember);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, alamat);
                            connection.pstat.setString(4, Jeniskelamin);
                            connection.pstat.setString(5, noTelp);
                            connection.pstat.setString(6, poin);
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
        txtnoTelp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (txtnoTelp.getText().length() >= 13) {
                    e.consume();
                }
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '\b') {
                    txtnoTelp.setEditable(true);
                } else {
                    txtnoTelp.setEditable(false);
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

    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_member,3))+1 FROM Member";
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
                kode = "MBR00"+autoid;
            }else if(autoid<100){
                kode = "MBR0"+autoid;
            }else{
                kode = "MBR"+autoid;
            }
            txtId.setText(kode);
            txtNama.requestFocus();
            txtnoTelp.setEnabled(true);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void clear(){
        txtId.setText("");
        txtNama.setText("");
        taalamat.setText("");
        rbLaki.setSelected(false);
        rbPerempuan.setSelected(false);
        txtnoTelp.setText("");
        txtpoin.setText("");
        autoid();
    }

    public void TM(){
        JFrame frame = new JFrame("TambahMember");
        frame.setContentPane(new TambahMember().tambahmember);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
