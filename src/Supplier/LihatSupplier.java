package Supplier;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
//import java.util.
import javax.swing.table.DefaultTableModel;

public class LihatSupplier {
    private JScrollPane view;
    private JTable tblSupplier;
    private JPanel lihatsupplier;
    private DefaultTableModel model;

    public LihatSupplier(){
        model = new DefaultTableModel();
        //menambahkan table model ke table
        tblSupplier.setModel(model);
        addColumn();
        loadData();
    }
    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Supplier";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);
                obj[4] = connection.result.getString(5);
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data"+ex);
        }
    }
    public void addColumn(){
        model.addColumn("ID Supplier");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("NO Telp");
        model.addColumn("Email");
    }
    public void ls() {
        JFrame frame = new JFrame("LihatSupplier");
        frame.setContentPane(new LihatSupplier().lihatsupplier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
