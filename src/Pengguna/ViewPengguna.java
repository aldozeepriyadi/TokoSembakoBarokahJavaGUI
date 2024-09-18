package Pengguna;

import Jabatan.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPengguna extends JFrame{
    private JScrollPane view;
    private JTable tblPengguna;
    private JPanel ViewPenguna;
    private JButton RefreshButton;
    private DefaultTableModel model;
    DBConnect connect = new DBConnect();

    public ViewPengguna(){
        model = new DefaultTableModel();
        tblPengguna.setModel(model);
        addColomn();
        loadData();




    }

    public void LP() {
        JFrame frame = new JFrame("ViewPengguna");
        frame.setContentPane(new ViewPengguna().ViewPenguna);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadData() {
        //menghapus seluruh data ditampilkan (jika ada) untuk tampilan pertama
        model.getDataVector().removeAllElements();

        //memberi tahu data telah kosong
        model.fireTableDataChanged();

        try{

            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM Pengguna";
            connect.result = connect.stat.executeQuery(query);

            //lakukan perbaris data
            while(connect.result.next()){
                Object[] obj = new Object[9];
                obj[0] = connect.result.getString("id_pengguna");
                obj[1] = connect.result.getString("id_jabatan");
                obj[2] = connect.result.getString("nama");
                obj[3] = connect.result.getString("noTelp");
                obj[4] = connect.result.getString("alamat");
                obj[5] = connect.result.getString("JenisKelamin");
                obj[6] = connect.result.getString("username");
                obj[7] = connect.result.getString("pass");
                model.addRow(obj);
            }
            connect.stat.close();
            connect.result.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Terjadi eror saat load data: " +e);
        }
    }
    public void addColomn(){
        model.addColumn("ID Pengguna");
        model.addColumn("ID Jabatan");
        model.addColumn("nama");
        model.addColumn("noTelp");
        model.addColumn("alamat");
        model.addColumn("JenisKelamin");
        model.addColumn("Username");
        model.addColumn("Password");


    }

}
