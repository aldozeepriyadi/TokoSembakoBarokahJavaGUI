package Hadiah;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LihatHadiah {
    private JPanel lihatmember;
    private JScrollPane view;
    private JTable tblHadiah;
    private JButton btnKembali;
    private JPanel LihatHadiah;
    private DefaultTableModel model;

    public void addColumn() {
        model.addColumn("ID Hadiah");
        model.addColumn("Hadiah");
        model.addColumn("Stok Hadiah");
        model.addColumn("Poin");
    }

    public LihatHadiah(){
        model = new DefaultTableModel();
        //menambahkan table model ke table
        tblHadiah.setModel(model);
        addColumn();
        loadData();

    }
    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM HadiahMember";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data"+ex);
        }
    }

    public void LH() {
        JFrame frame = new JFrame("LihatHadiah");
        frame.setContentPane(new LihatHadiah().LihatHadiah);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
