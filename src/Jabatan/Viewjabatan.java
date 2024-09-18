package Jabatan;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Viewjabatan {
    private JScrollPane view;
    private JTable tblJabatan;
    private JPanel ViewJabatan;
    private JButton RefreshButton;
    private DefaultTableModel model;
    DBConnection_06 connect = new DBConnection_06();


    public Viewjabatan(){

        model = new DefaultTableModel();
        tblJabatan.setModel(model);

        addColomn();
        loadData();


    }
    private void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Jabatan";
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
    public void addColomn(){
        model.addColumn("ID Jabatan");
        model.addColumn("Jabatan");
    }

    public void LJ() {
        JFrame frame = new JFrame("Viewjabatan");
        frame.setContentPane(new Viewjabatan().ViewJabatan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
