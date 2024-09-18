package Member;

import DBConnection_06.DBConnection_06;
import Form.FormMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LihatMember {
    private JScrollPane view;
    private JTable tblMember;
    private JButton btnKembali;
    private JPanel lihatmember;
    private DefaultTableModel model;


    public void addColomn(){
        model.addColumn("ID Member");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Jenis Kelamin");
        model.addColumn("No Telepon");
        model.addColumn("Poin");

    }

    public LihatMember(){
        //membuat table model
        model = new DefaultTableModel();

        //menambahkan table model ke table
        tblMember.setModel(model);
        //memanggil method
        addColomn();
        loadData();

    }

    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnection_06 connection = new DBConnection_06();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM Member";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[6];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);
                obj[4] = connection.result.getString(5);
                obj[5] = connection.result.getString(6);

                model.addRow(obj);

            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data"+ex);
        }
    }

    public void LM() {
        JFrame frame = new JFrame("LihatMember");
        frame.setContentPane(new LihatMember().lihatmember);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
