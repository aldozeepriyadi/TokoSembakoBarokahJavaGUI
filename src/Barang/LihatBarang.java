package Barang;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LihatBarang {
    private JPanel lihatsupplier;
    private JScrollPane view;
    private JTable tblBarang;
    private JPanel LihatBarang;
    private DefaultTableModel model;

    public void addColumn() {
        model.addColumn("ID Barang");
        model.addColumn("ID Merk");
        model.addColumn("ID Satuan");
        model.addColumn("Nama");
        model.addColumn("Stok");
        model.addColumn("Harga Jual");
        model.addColumn("Harga Grosir");
    }
    public LihatBarang(){
        model = new DefaultTableModel();
        //menambahkan table model ke table
        tblBarang.setModel(model);

        addColumn();
        loadData();

    }

    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Barang";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[7];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);
                obj[4] = connection.result.getString(5);
                obj[5] = connection.result.getString(6);
                obj[6] = connection.result.getString(7);
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data"+ex);
        }
    }

    public void LB() {
        JFrame frame = new JFrame("LihatBarang");
        frame.setContentPane(new LihatBarang().LihatBarang);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
