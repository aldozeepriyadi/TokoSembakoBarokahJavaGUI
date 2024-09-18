package Promo;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewPromo extends JFrame{
    private JScrollPane view;
    private JTable tblPromo;
    private JPanel ViewPromo;
    private JButton btnCariPgg;
    private JTextField txtCariIDPromo;
    private DefaultTableModel model;
    DBConnection_06 connection = new DBConnection_06();

    public ViewPromo(){
        model = new DefaultTableModel();
        tblPromo.setModel(model);

        addColumn();
        loadData();

    }

    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Promo";
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
        model.addColumn("ID Promo");
        model.addColumn("Nama Promo");
        model.addColumn("Discount");
        model.addColumn("Tanggal Awal");
        model.addColumn("Tanggal Berakhir");
    }

    public void LP() {
        JFrame frame = new JFrame("View Promo");
        frame.setContentPane(new ViewPromo().ViewPromo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
