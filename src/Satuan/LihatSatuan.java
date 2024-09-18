package Satuan;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LihatSatuan {
    private JPanel LihatSatuan;
    private JPanel LihatMerk;
    private JScrollPane view;
    private JTable tblSatuan;
    private DefaultTableModel model;


    public void addColumn() {
        model.addColumn("ID Satuan");
        model.addColumn("Satuan");
    }

    public LihatSatuan(){
        model = new DefaultTableModel();
        //menambahkan table model ke table
        tblSatuan.setModel(model);

        addColumn();
        loadData();
    }

    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM satuan";
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


    public void ls() {
        JFrame frame = new JFrame("Lihat Satuan");
        frame.setContentPane(new LihatSatuan().LihatSatuan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
