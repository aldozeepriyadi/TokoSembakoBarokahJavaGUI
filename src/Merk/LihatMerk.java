package Merk;

import DBConnection_06.DBConnection_06;
import Form.FromMerk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LihatMerk {
    private JScrollPane view;
    private JTable tblMerk;
    private JButton btnKembali;
    private JPanel LihatMerk;
    private DefaultTableModel model;

    public void addColumn() {
        model.addColumn("ID Merk");
        model.addColumn("Merk");
    }

    public LihatMerk(){
        model = new DefaultTableModel();
        //menambahkan table model ke table
        tblMerk.setModel(model);

        addColumn();
        loadData();


    }

    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Merk";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[2];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                model.addRow(obj);

            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data"+ex);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LihatMerk");
        frame.setContentPane(new LihatMerk().LihatMerk);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void lm() {
        JFrame frame = new JFrame("LihatMerk");
        frame.setContentPane(new LihatMerk().LihatMerk);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
